package com.example.search.domain.search.infrastructure.external;

import com.example.search.domain.search.infrastructure.external.dto.NaverDto;
import com.example.search.domain.search.infrastructure.external.dto.NaverSearchResponse;
import com.example.search.domain.search.dto.SearchResultDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class NaverSearchClientImpl implements NaverSearchClient{

    private final RestTemplate restTemplate;
    private final String clientId;
    private final String clientSecret;
    private final String searchUrl;

    public NaverSearchClientImpl(
            RestTemplate restTemplate,
            @Value("${naver.api.client-id}") String clientId,
            @Value("${naver.api.client-secret}") String clientSecret,
            @Value("${naver.api.search-url}") String searchUrl
    ) {
        this.restTemplate = restTemplate;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.searchUrl = searchUrl;
    }

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
                            .longitude(parseOrZero(item.getMapx()))
                            .latitude(parseOrZero(item.getMapy()))
                            .build())
                    .toList();

        } catch (Exception e) {
            log.error("Naver API 호출 실패: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    private Double parseOrZero(String value) {
        try {
            return Double.parseDouble(value);
        } catch (Exception e) {
            return 0.0;
        }
    }
}
