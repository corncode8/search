package com.example.search.api.search.service;

import com.example.search.domain.infrastructure.external.KakaoSearchClient;
import com.example.search.domain.infrastructure.external.NaverSearchClient;
import com.example.search.domain.search.dto.SearchResultDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchClientImpl implements SearchClient {

    private final KakaoSearchClient kakaoSearchClient;
    private final NaverSearchClient naverSearchClient;

    @Override
    public List<SearchResultDto> search(String keyword, String location) {

        // 1) Naver
        try {
            List<SearchResultDto> naver = naverSearchClient.search(keyword, location);
            if (naver != null && !naver.isEmpty()) {
                return naver;
            }
        } catch (Exception e) {
            log.warn("Naver Client 실패 : {}", e.getMessage());

        }

        // 2) Kakao (fallback)
        try {
            List<SearchResultDto> kakao = kakaoSearchClient.search(keyword);
            if (kakao != null && !kakao.isEmpty()) {
                return kakao;
            } else {
                return Collections.emptyList();
            }
        } catch (Exception e) {
            log.warn("Kakao Client 실패 : {}", e.getMessage());
            return Collections.emptyList();
        }

    }

}
