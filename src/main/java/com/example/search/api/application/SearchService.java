package com.example.search.api.application;

import com.example.search.api.data.response.PageResponse;
import com.example.search.api.data.response.PopularKeywordsResponse;
import com.example.search.domain.search.dto.SearchResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final SearchClientManager clienclientManager;
    private final KeywordStore keywordStore;
    private final RedisKeywordManager redisManager;

    public PageResponse<SearchResultDto> search(String keyword, String location) {
        List<SearchResultDto> items = clienclientManager.search(keyword, location);

        keywordStore.save(keyword, location);
        redisManager.saveRedis(keyword, location);

        return PageResponse.<SearchResultDto>builder()
                .items(items)
                .totalItems(items.size())
                .totalPages(1)
                .currentPage(1)
                .itemsPerPage(items.size())
                .hasPreviousPage(false)
                .hasNextPage(false)
                .build();

    }

    public PopularKeywordsResponse popularKeywords() {
        Map<String, Integer> result = redisManager.selectPopularKeywords();

        return PopularKeywordsResponse.builder()
                .keywordList(result)
                .build();
    }
}
