package com.example.search.domain.infrastructure.external;

import com.example.search.domain.search.dto.SearchResultDto;

import java.util.List;

public interface NaverSearchClient {

    List<SearchResultDto> search(String keyword, String location);
}
