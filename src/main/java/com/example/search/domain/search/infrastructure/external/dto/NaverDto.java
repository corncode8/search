package com.example.search.domain.search.infrastructure.external.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class NaverDto {

    private String title;
    private String link;
    private String category;
    private String description;
    private String telephone;
    private String address;
    private String roadAddress;

    @JsonProperty("mapx")
    private String mapx;
    @JsonProperty("mapy")
    private String mapy;
}
