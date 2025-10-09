package com.example.search.domain.history.repository;

public interface SearchHistoryStoreRepository {

    void save (String keyword, String location);
}
