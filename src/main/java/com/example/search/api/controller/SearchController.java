package com.example.search.api.controller;

import com.example.search.api.data.response.PageResponse;
import com.example.search.api.data.response.PopularKeywordsResponse;
import com.example.search.api.data.response.SearchResponse;
import com.example.search.api.application.SearchService;
import com.example.search.domain.search.dto.SearchResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/search")
    public SearchResponse search(@RequestParam String keyword, @RequestParam String location) {
        PageResponse<SearchResultDto> page = searchService.search(keyword, location);

        return SearchResponse.builder()
                .status(200)
                .message("success")
                .data(page)
                .build();
    }

    @GetMapping("/popular")
    public SearchResponse popularKeywords() {
        PopularKeywordsResponse popularKeywords = searchService.popularKeywords();

        return SearchResponse.builder()
                .status(200)
                .message("success")
                .data(popularKeywords)
                .build();
    }
}
