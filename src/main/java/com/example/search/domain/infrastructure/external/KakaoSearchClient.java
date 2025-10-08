package com.example.search.domain.infrastructure.external;


import com.example.search.domain.search.dto.SearchResultDto;

import java.util.List;

public interface KakaoSearchClient {

    List<SearchResultDto> search(String keyword);
}
