package com.example.search.api.application;

import com.example.search.domain.history.repository.SearchHistoryStoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class KeywordStore {

    private final SearchHistoryStoreRepository repository;


    public void save(String keyword, String location) {
        repository.save(keyword, location);
    }


}
