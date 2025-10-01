package com.example.search.domain.history.infrastructure;

import com.example.search.domain.history.repository.SearchHistoryStoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SearchHistoryCoreStoryRepository implements SearchHistoryStoreRepository {

    private final SearchHistoryJpaRepository searchHistoryJpaRepository;
}
