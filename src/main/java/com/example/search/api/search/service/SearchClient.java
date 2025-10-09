package com.example.search.api.search.service;

import com.example.search.domain.search.dto.SearchResultDto;

import java.util.List;

// 외부 API 호출 인터페이스
public interface SearchClient {
    List<SearchResultDto> search(String keyword, String location);
}
