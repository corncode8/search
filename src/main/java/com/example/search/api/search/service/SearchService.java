package com.example.search.api.search.service;

import com.example.search.api.response.PageResponse;
import com.example.search.domain.search.dto.SearchResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final SearchClientManager clienclientManager;
    private final KeywordRecoder keywordRecoder;

    public PageResponse<SearchResultDto> search(String keyword, String location) {
        List<SearchResultDto> items = clienclientManager.search(keyword, location);

        keywordRecoder.dbRecord(keyword, location);

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
}
