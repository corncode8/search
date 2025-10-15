package com.example.search.api.application;

import com.example.search.domain.search.dto.SearchResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchClientManager {

    private final List<SearchClientImpl> clients;

    public List<SearchResultDto> search(String keyword, String location) {
        for (SearchClientImpl client : clients) {
            try {
                List<SearchResultDto> result = client.search(keyword, location);
                if (!result.isEmpty()) {
                    return result;
                }
            } catch (Exception e) {
                System.err.println("Problem : SearchClient 실패  " + e.getMessage());
            }
        }
        return Collections.emptyList();
    }
}
