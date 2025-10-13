package com.example.search.domain.search.infrastructure.external;

import com.example.search.domain.search.dto.SearchResultDto;
import lombok.extern.slf4j.Slf4j;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@Slf4j
class NaverSearchClientImplTest {

    private static MockWebServer mockWebServer;
    private NaverSearchClientImpl client;


    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        RestTemplate restTemplate = new RestTemplate(new SimpleClientHttpRequestFactory());
        String baseUrl = mockWebServer.url("/").toString();
        client = new NaverSearchClientImpl(
                restTemplate,
                "testClient",
                "testClientSecret",
                baseUrl
        );

    }

    @AfterAll
    static void shutdown() throws Exception {
        mockWebServer.shutdown();
    }

    @Test
    @DisplayName("Naver Api Test")
    void 네이버_API_Test() {
        // given
        String mockBody = """
            {
              "items": [
                {
                  "title": "꿉당",
                  "category": "한식>육류,고기요리",
                  "address": "서울특별시 서초구 잠원동 12-21 1층",
                  "roadAddress": "서울특별시 서초구 강남대로 615 1층",
                  "latitude": "127.019",
                  "longitude": "37.516"
                }
              ]
            }
        """;

        mockWebServer.enqueue(new MockResponse()
                .setBody(mockBody)
                .addHeader("Content-Type", "application/json"));

        // when
        List<SearchResultDto> results = client.search("꿉당", "서초구");

        // then
        assertThat(results).hasSize(1);
        SearchResultDto result = results.get(0);
        assertThat(result.getTitle()).contains("꿉당");
        assertThat(result.getCategory()).isEqualTo("한식>육류,고기요리");
        assertThat(result.getAddress()).contains("서초구");
        assertThat(result.getRoadAddress()).contains("강남대로");
        assertThat(result.getLatitude()).isEqualTo(127.019);
        assertThat(result.getLongitude()).isEqualTo(375.166);

    }

}
