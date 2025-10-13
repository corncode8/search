package com.example.search.api.application;

import com.example.search.api.data.response.PageResponse;
import com.example.search.api.data.response.PopularKeywordsResponse;
import com.example.search.domain.search.dto.SearchResultDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SearchServiceTest {

    @Mock
    SearchClientManager clienclientManager;
    @Mock
    KeywordStore keywordStore;
    @Mock
    StringRedisTemplate redisTemplate;
    @Mock
    ZSetOperations<String, String> zsetOps;
    @Mock
    RedisKeywordManager redisManager;

    @InjectMocks
    SearchService searchService;

    @Test
    @DisplayName("search 성공 테스트")
    void search_Test() {
        //given
        String keyword = "삽겹살";
        String location = "양재";

        when(clienclientManager.search(keyword, location))
                .thenReturn(List.of(SearchResultDto.builder()
                        .title("판교집 양재 직영점")
                        .category("한식")
                        .address("서울특별시 서초구 양재동 272-7 1층")
                        .roadAddress("서울특별시 서초구 마방로10길 58 1층")
                        .latitude(37.475)
                        .longitude(127.045)
                        .build()));

        // when
        PageResponse<SearchResultDto> response = searchService.search(keyword, location);

        // then
        verify(clienclientManager).search(keyword, location);
        verify(keywordStore).save(keyword, location);
        verify(redisManager).saveRedis(keyword, location);

        assertEquals("판교집 양재 직영점", response.getItems().get(0).getTitle());
    }

    @Test
    @DisplayName("popularKeywords 성공 테스트")
    void popularKeywordsTest() {
        // given
        Map<String, Integer> map = new HashMap<>();
        map.put("국밥", 42);
        map.put("피자", 35);
        map.put("삼겹살", 1000);
        map.put("카페", 200);
        map.put("초밥", 500);
        when(redisManager.selectPopularKeywords()).thenReturn(map);

        // when
        PopularKeywordsResponse response = searchService.popularKeywords();

        // then
        verify(redisManager).selectPopularKeywords();
        assertEquals(5, response.getKeywordList().size());
        assertEquals(map, response.getKeywordList());

    }
}
