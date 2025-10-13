package com.example.search.domain.search.infrastructure.external;

import com.example.search.domain.search.dto.SearchResultDto;
import lombok.extern.slf4j.Slf4j;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


@Slf4j
class KakaoSearchClientImplTest {

    private static MockWebServer mockWebServer;
    private KakaoSearchClientImpl client;


    @BeforeEach
    void setUp() throws Exception {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        RestClient.Builder builder = RestClient.builder().baseUrl(mockWebServer.url("/").toString());
        client = new KakaoSearchClientImpl(builder, "testApiKey");
    }

    @AfterAll
    static void shutdown() throws Exception {
        mockWebServer.shutdown();
    }

    @Test
    @DisplayName("Kakao Api Test")
    void 카카오_API_test() {
        //given
        String keyword = "꿉당 서초구";

        //when
        List<SearchResultDto> results = client.search(keyword);

        //then
        assertThat(results).hasSize(3);
        results.forEach(result -> assertThat(result.getTitle().contains("꿉당 신사점")));

    }
}
