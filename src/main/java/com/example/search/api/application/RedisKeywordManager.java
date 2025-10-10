package com.example.search.api.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisKeywordManager {

    private final StringRedisTemplate redisTemplate;

    public Map<String, Integer> selectPopularKeywords(String location) {
        String todayKey = "popular_keywords:" + LocalDate.now() + ":" + location;
        String yesterdayKey = "popular_keywords:" + LocalDate.now().minusDays(1) + ":" + location;

        Long today = redisTemplate.opsForZSet().size(todayKey);
        String targetKey = (today != null && today > 100) ? todayKey : yesterdayKey;

        Set<ZSetOperations.TypedTuple<String>> topKeywords =
                redisTemplate.opsForZSet().reverseRangeWithScores(targetKey, 0, 9);

        Map<String, Integer> keywordList = new LinkedHashMap<>();
        if (topKeywords != null) {
            for (ZSetOperations.TypedTuple<String> tuple : topKeywords) {
                keywordList.put(tuple.getValue(), tuple.getScore().intValue());
            }
        }

        return keywordList;
    }

    public void saveRedis(String keyword, String location) {
        String key = "popular_keywards : " + LocalDate.now() + ":" + location;

        redisTemplate.opsForZSet().incrementScore(key, keyword, 1.0);

        // 키 만료 2일
        redisTemplate.expire(key, Duration.ofDays(2));
    }
}
