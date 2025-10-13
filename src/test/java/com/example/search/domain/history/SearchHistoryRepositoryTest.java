package com.example.search.domain.history;

import com.example.search.domain.history.entity.SearchHistory;
import com.example.search.domain.history.infrastructure.SearchHistoryCoreStoreRepository;
import com.example.search.domain.history.infrastructure.SearchHistoryJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SearchHistoryRepositoryTest {

    @Mock
    SearchHistoryJpaRepository jpaRepository;

    @InjectMocks
    SearchHistoryCoreStoreRepository repository;

    @Test
    @DisplayName("Save Test")
    void save() {
        //given
        String keyword = "꿉당";
        String location = "서초구";

        SearchHistory history = SearchHistory.builder()
                .keyword(keyword)
                .location(location)
                .build();

        when(jpaRepository.save(any(SearchHistory.class))).thenReturn(history);

        //when
        SearchHistory result = repository.save(keyword, location);

        //then
        assertNotNull(result);
        assertEquals(history.getKeyword(), result.getKeyword());
        assertEquals(history.getLocation(), result.getLocation());
    }
}
