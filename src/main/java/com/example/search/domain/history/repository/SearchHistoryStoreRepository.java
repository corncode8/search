package com.example.search.domain.history.repository;

import com.example.search.domain.history.entity.SearchHistory;

public interface SearchHistoryStoreRepository {

    SearchHistory save (String keyword, String location);
}
