package com.example.search.api.application;

import com.example.search.domain.search.dto.SearchResultDto;
import com.example.search.domain.search.infrastructure.external.KakaoSearchClient;
import com.example.search.domain.search.infrastructure.external.NaverSearchClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SearchClientImplTest {

    @Mock
    KakaoSearchClient kakaoSearchClient;
    @Mock
    NaverSearchClient naverSearchClient;

    @InjectMocks
    SearchClientImpl client;

    @Test
    @DisplayName("search 성공 테스트")
    void SearchClient_search_성공_테스트() {
        //given
        String keyword = "꿉당";
        String location = "서초구";
        SearchResultDto naverResult = SearchResultDto.builder()
                .title("꿉당")
                .category("한식")
                .address("서초구")
                .roadAddress("강남대로")
                .build();
        SearchResultDto kakaoResult = SearchResultDto.builder()
                .title("꿉당")
                .category("한식")
                .address("서초구")
                .roadAddress("강남대로")
                .build();

        when(naverSearchClient.search(keyword, location))
                .thenReturn(Arrays.asList(naverResult));
        when(kakaoSearchClient.search(keyword))
                .thenReturn(Arrays.asList(kakaoResult));

        //when
        List<SearchResultDto> result = client.search(keyword, location);

        //then
        assertEquals(2, result.size());
        assertTrue(result.contains(naverResult));
        assertTrue(result.contains(kakaoResult));
    }

    // 네이버 API return 값이 없는 경우
    @Test
    @DisplayName("search 실패 테스트 - 네이버 실패")
    void search_Failover_Test() {
        //given
        String keyword = "꿉당";
        String location = "서초구";

        SearchResultDto kakaoResult = SearchResultDto.builder()
                .title("꿉당")
                .category("한식")
                .address("서초구")
                .roadAddress("강남대로")
                .build();

        when(kakaoSearchClient.search(keyword))
                .thenReturn(Arrays.asList(kakaoResult));

        //when
        List<SearchResultDto> result = client.search(keyword, location);

        //then
        assertEquals(1, result.size());
        assertTrue(result.contains(kakaoResult));

    }

    // 카카오 API return 값이 없는 경우
    @Test
    @DisplayName("search 실패 테스트 - 네이버 실패")
    void search_Failover_TestV2() {
        //given
        String keyword = "꿉당";
        String location = "서초구";

        SearchResultDto naverResult = SearchResultDto.builder()
                .title("꿉당")
                .category("한식")
                .address("서초구")
                .roadAddress("강남대로")
                .build();

        when(naverSearchClient.search(keyword, location))
                .thenReturn(Arrays.asList(naverResult));

        //when
        List<SearchResultDto> result = client.search(keyword, location);

        //then
        assertEquals(1, result.size());
        assertTrue(result.contains(naverResult));


    }

    // 네이버, 카카오 모두 실패할 경우 emptyList 반환
    @Test
    @DisplayName("네이버, 카카오 모두 실패할 경우 emptyList 반환")
    void failTest() {
        //given
        String keyword = "꿉당";
        String location = "서초구";

        //when
        List<SearchResultDto> result = client.search(keyword, location);

        //then
        assertEquals(0, result.size());

    }

}
