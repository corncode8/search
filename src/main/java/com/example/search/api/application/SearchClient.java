package com.example.search.api.application;

import com.example.search.domain.search.dto.SearchResultDto;

import java.util.List;

public interface SearchClient {
    List<SearchResultDto> search(String keyword, String location);
}
