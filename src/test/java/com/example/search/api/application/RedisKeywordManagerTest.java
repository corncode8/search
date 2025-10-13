package com.example.search.api.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RedisKeywordManagerTest {

    @Mock
    StringRedisTemplate redisTemplate;

    @Mock
    ZSetOperations<String, String> zsetOps;

    @InjectMocks
    RedisKeywordManager keywordManager;

    @Test
    @DisplayName("오늘 데이터로 Top10 조회")
    void selectPopularKeywords_useTodayKey() {
        // given
        String todayKey = "popular_keywords:" + LocalDate.now();
        when(redisTemplate.opsForZSet()).thenReturn(zsetOps);
        when(zsetOps.size(todayKey)).thenReturn(150L);

        Set<ZSetOperations.TypedTuple<String>> tuples = new LinkedHashSet<>();
        tuples.add(new DefaultTypedTuple<>("국밥", 42.0));
        tuples.add(new DefaultTypedTuple<>("피자", 35.0));
        tuples.add(new DefaultTypedTuple<>("삼겹살", 150.0));
        tuples.add(new DefaultTypedTuple<>("치킨", 100.0));
        tuples.add(new DefaultTypedTuple<>("초밥", 120.0));
        tuples.add(new DefaultTypedTuple<>("족발", 60.0));
        tuples.add(new DefaultTypedTuple<>("파스타", 80.0));
        tuples.add(new DefaultTypedTuple<>("김밥", 20.0));
        tuples.add(new DefaultTypedTuple<>("스테이크", 80.0));
        tuples.add(new DefaultTypedTuple<>("햄버거", 101.0));
        when(zsetOps.reverseRangeWithScores(eq(todayKey), eq(0L), eq(9L))).thenReturn(tuples);

        // when
        Map<String, Integer> result = keywordManager.selectPopularKeywords();

        // then
        verify(zsetOps).size(todayKey);
        verify(zsetOps).reverseRangeWithScores(todayKey, 0, 9);
        assertEquals(10, result.size());
        assertEquals(42, result.get("국밥"));
        assertEquals(35, result.get("피자"));
        assertEquals(150, result.get("삼겹살"));

    }

    @Test
    @DisplayName("오늘 데이터가 부족하면 어제 키로 Top10 조회")
    void selectPopularKeywords_useYesterdayKeyKey() {
        //given
        String baseKey = "popular_keywords:";
        String todayKey = baseKey + LocalDate.now();
        String yesterdayKey = baseKey + LocalDate.now().minusDays(1);
        when(redisTemplate.opsForZSet()).thenReturn(zsetOps);
        when(zsetOps.size(todayKey)).thenReturn(50L); // 100개 미만이기 때문에 어제 키워드 사용

        Set<ZSetOperations.TypedTuple<String>> tuples = new LinkedHashSet<>();
        tuples.add(new DefaultTypedTuple<>("라면", 20.0));
        tuples.add(new DefaultTypedTuple<>("삼겹살", 150.0));
        tuples.add(new DefaultTypedTuple<>("치킨", 100.0));
        tuples.add(new DefaultTypedTuple<>("초밥", 120.0));
        when(zsetOps.reverseRangeWithScores(eq(yesterdayKey), eq(0L), eq(9L))).thenReturn(tuples);

        // when
        Map<String, Integer> result = keywordManager.selectPopularKeywords();

        // then
        verify(zsetOps).size(todayKey);
        verify(zsetOps).reverseRangeWithScores(yesterdayKey, 0, 9);
        assertEquals(4, result.size());
        assertEquals(20, result.get("라면"));

        // 1등 키워드 검증
        Map.Entry<String, Integer> top = result.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .orElse(null);

        assertEquals("삼겹살", top.getKey());
        assertEquals(150, top.getValue());
    }
}
