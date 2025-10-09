package com.example.search.api.search;

import com.example.search.api.response.PageResponse;
import com.example.search.api.response.SearchResponse;
import com.example.search.api.search.service.SearchService;
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
}
