package com.example.search.api.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisKeywordManager {

    private final StringRedisTemplate redisTemplate;

    private static final String DAILY_KEY = "popular_keywords:";
    private static final Duration KEY_TTL = Duration.ofDays(2);

    public Map<String, Integer> selectPopularKeywords() {
        String todayKey = DAILY_KEY + LocalDate.now();
        String yesterdayKey = DAILY_KEY + LocalDate.now().minusDays(1);

        Long todaySize = redisTemplate.opsForZSet().size(todayKey);
        String targetKey = (todaySize != null && todaySize > 100) ? todayKey : yesterdayKey;

        Set<ZSetOperations.TypedTuple<String>> topKeywords =
                redisTemplate.opsForZSet().reverseRangeWithScores(targetKey, 0, 9);

        Map<String, Integer> keywordList = new LinkedHashMap<>();
        if (topKeywords != null) {
            for (ZSetOperations.TypedTuple<String> tuple : topKeywords) {
                if (tuple.getValue() != null && tuple.getScore() != null) {
                    keywordList.put(tuple.getValue(), tuple.getScore().intValue());
                }
            }
        }

        return keywordList;
    }

    public void saveRedis(String keyword, String location) {
        String baseKey = DAILY_KEY + LocalDate.now();
        String locationKey = baseKey + ":" + location;

        redisTemplate.opsForZSet().incrementScore(baseKey, keyword, 1.0);
        redisTemplate.opsForZSet().incrementScore(locationKey, keyword, 1.0);

        redisTemplate.expire(baseKey, KEY_TTL);
        redisTemplate.expire(locationKey, KEY_TTL);
    }
}
