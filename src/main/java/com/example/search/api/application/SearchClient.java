package com.example.search.api.application;

import com.example.search.domain.search.infrastructure.external.KakaoSearchClient;
import com.example.search.domain.search.infrastructure.external.NaverSearchClient;
import com.example.search.domain.search.dto.SearchResultDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchClient {

    private final KakaoSearchClient kakaoSearchClient;
    private final NaverSearchClient naverSearchClient;

    public List<SearchResultDto> search(String keyword, String location) {
        List<SearchResultDto> result = new ArrayList<>();

        // 1) Naver
        try {
            List<SearchResultDto> naver = naverSearchClient.search(keyword, location);
            if (naver != null && !naver.isEmpty()) {
                result.addAll(naver);
            }
        } catch (Exception e) {
            log.warn("Naver Client 실패 : {}", e.getMessage());

        }

        // 2) Kakao (fallback)
        try {
            List<SearchResultDto> kakao = kakaoSearchClient.search(keyword);
            if (kakao != null && !kakao.isEmpty()) {
                result.addAll(kakao);
            } else {
                return Collections.emptyList();
            }
        } catch (Exception e) {
            log.warn("Kakao Client 실패 : {}", e.getMessage());
        }

        return result;

    }

}
