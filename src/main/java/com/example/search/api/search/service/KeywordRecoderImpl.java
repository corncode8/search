package com.example.search.api.search.service;

import com.example.search.domain.history.repository.SearchHistoryStoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KeywordRecoderImpl implements KeywordRecoder {

    private final SearchHistoryStoreRepository repository;

    @Override
    public void dbRecord(String keyword, String location) {
        repository.save(keyword, location);
    }

    @Override
    public void redisRecord(String keyword, String location) {

    }
}
