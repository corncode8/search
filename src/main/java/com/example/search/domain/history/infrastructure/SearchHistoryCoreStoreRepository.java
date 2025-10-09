package com.example.search.domain.history.infrastructure;

import com.example.search.domain.history.entity.SearchHistory;
import com.example.search.domain.history.repository.SearchHistoryStoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SearchHistoryCoreStoreRepository implements SearchHistoryStoreRepository {

    private final SearchHistoryJpaRepository searchHistoryJpaRepository;

    @Override
    public void save(String keyword, String location) {
        searchHistoryJpaRepository.save(
                SearchHistory.builder()
                        .keyword(keyword)
                        .location(location)
                        .build()
        );
    }
}
