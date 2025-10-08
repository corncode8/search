package com.example.search.domain.infrastructure.external.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class NaverSearchResponse {
    private String lastBuildDate;
    private int total;
    private int start;
    private int display;
    private List<NaverDto> items;
}
