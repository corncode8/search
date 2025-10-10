package com.example.search.api.data.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
@Builder
public class PopularKeywordsResponse {

    private Map<String, Integer> keywordList;
}
