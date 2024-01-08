package com.example.demo.utils.redis;

import java.time.Duration;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.utils.ClassConvert;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class LoginFailedRepository {

	private final RedisTemplate<String, Object> redisTemplate;
	private final Long TIME_TO_LIVE = 8400000L;
	private static final String PREFIX_FOR_KEY = "ULF: ";
	
	public void setValue(String nickname, Integer failedCount) {
		String key = getKey(nickname);
		redisTemplate.opsForValue().set(key, failedCount, Duration.ofMillis(TIME_TO_LIVE));
	}
	
	public Integer getValues(String loginId) {
		return ClassConvert.castingInstance(
				redisTemplate.opsForValue().get(getKey(loginId)), 
				Integer.class);
	}
	
	public String getKey(String loginId) {
		return PREFIX_FOR_KEY + loginId;
	}
	
	public Long increment(String loginId) {
		return redisTemplate.opsForValue().increment(getKey(loginId));
	}
	
	public void delete(String loginId) {
		redisTemplate.delete(getKey(loginId));
	}
}
