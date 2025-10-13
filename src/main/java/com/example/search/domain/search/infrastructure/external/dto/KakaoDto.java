package com.example.search.domain.search.infrastructure.external.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoDto {

    @JsonProperty("address_name")
    private String addressName;     // 서울 서초구 잠원동 12-21

    @JsonProperty("place_name")
    private String palceName;       // 꿉당 신사점

    @JsonProperty("category_name")
    private String categoryName;    // 음식점 > 한식 > 육류,고기

    @JsonProperty("category_group_name")
    private String category;        // 음식점

    @JsonProperty("road_address_name")
    private String roadAddressName; // 서울 서초구 강남대로 615

    private String x; // longitude
    private String y; // latitude
}
