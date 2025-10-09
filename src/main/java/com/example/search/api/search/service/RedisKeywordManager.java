package com.example.search.api.search.service;


import java.util.Map;

public interface RedisKeywordManager {

    void saveRedis(String keyword, String location);

    Map<String, Integer> selectPopularKeywords(String location);
}
