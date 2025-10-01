package com.example.search.domain.history.infrastructure;

import com.example.search.domain.history.entity.SearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchHistoryJpaRepository extends JpaRepository<SearchHistory, Long> {
}
