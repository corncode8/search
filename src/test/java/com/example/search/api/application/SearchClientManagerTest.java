package com.example.search.api.application;

import com.example.search.domain.search.dto.SearchResultDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class SearchClientManagerTest {

    @Mock
    SearchClient naverClient;

    @Mock
    SearchClient kakaoClient;

    @InjectMocks
    SearchClientManager searchClientManager;

    @Test
    @DisplayName("네이버 Client 성공")
    void success_Naver() {
        //given
        String keyword = "꿉당";
        String location = "서초구";

        SearchResultDto naverResult = SearchResultDto.builder()
                .title("꿉당")
                .category("한식")
                .address("서초구")
                .roadAddress("강남대로")
                .build();

        when(naverClient.search(keyword, location)).thenReturn(Arrays.asList(naverResult));
        searchClientManager = new SearchClientManager(Arrays.asList(naverClient, kakaoClient));

        //when
        List<SearchResultDto> result = searchClientManager.search(keyword, location);

        //then
        assertEquals(1, result.size());
        assertEquals(naverResult, result.get(0));
        verify(naverClient).search(keyword, location);
        verifyNoInteractions(kakaoClient);

    }

    @Test
    @DisplayName("카카오 Client 성공")
    void success_Kakao() {
        //given
        String keyword = "꿉당";
        String location = "서초구";

        SearchResultDto kakaoResult = SearchResultDto.builder()
                .title("꿉당")
                .category("한식")
                .address("서초구")
                .roadAddress("강남대로")
                .build();

        when(naverClient.search(keyword, location)).thenThrow(new RuntimeException("네이버 장애"));
        when(kakaoClient.search(keyword, location)).thenReturn(Arrays.asList(kakaoResult));
        searchClientManager = new SearchClientManager(Arrays.asList(naverClient, kakaoClient));

        //when
        List<SearchResultDto> result = searchClientManager.search(keyword, location);

        //then
        assertEquals(1, result.size());
        assertEquals(kakaoResult, result.get(0));
        verify(kakaoClient).search(keyword, location);

    }
}
