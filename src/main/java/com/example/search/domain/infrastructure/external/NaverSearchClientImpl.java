package com.example.search.domain.infrastructure.external;

import com.example.search.domain.infrastructure.external.dto.NaverDto;
import com.example.search.domain.infrastructure.external.dto.NaverSearchResponse;
import com.example.search.domain.search.dto.SearchResultDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.Collections;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class NaverSearchClientImpl implements NaverSearchClient{

    private final RestTemplate restTemplate;

    @Value("${naver.api.client-id}")
    String clientId;

    @Value("${naver.api.client-secret}")
    String clientSecret;

    @Value("${naver.api.search-url}")
    String searchUrl;

    @Override
    public List<SearchResultDto> search(String keyword, String location) {
        String query = keyword + " " + location;

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", clientId);
        headers.set("X-Naver-Client-Secret", clientSecret);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        String requestUrl = searchUrl + "?query=" + query;

        try {
            ResponseEntity<NaverSearchResponse> response = restTemplate.exchange(
                    requestUrl,
                    HttpMethod.GET,
                    requestEntity,
                    NaverSearchResponse.class
            );

            List<NaverDto> items = response.getBody().getItems();

            return items.stream()
                    .map(item -> SearchResultDto.builder()
                            .title(item.getTitle())
                            .category(item.getCategory())
                            .address(item.getAddress())
                            .roadAddress(item.getRoadAddress())
                            .latitude(parseOrZero(item.getY()))
                            .longitude(parseOrZero(item.getX()))
                            .build())
                    .toList();

        } catch (Exception e) {
            log.error("Naver API 호출 실패: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    private double parseOrZero(String value) {
        try {
            return Double.parseDouble(value);
        } catch (Exception e) {
            return 0.0;
        }
    }
}
