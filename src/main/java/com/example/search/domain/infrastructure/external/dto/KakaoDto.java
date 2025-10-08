package com.example.search.domain.infrastructure.external.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoDto {

    @JsonProperty("place_name")
    private String placeName;

    @JsonProperty("address_name")
    private String addressName;

    @JsonProperty("road_address_name")
    private String roadAddressName;

    @JsonProperty("category_name")
    private String categoryName;

    private String x; // longitude
    private String y; // latitude
}
