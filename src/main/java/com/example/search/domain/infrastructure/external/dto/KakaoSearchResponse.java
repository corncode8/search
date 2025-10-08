package com.example.search.domain.infrastructure.external.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class KakaoSearchResponse {

    private List<KakaoDto> documents;
}
