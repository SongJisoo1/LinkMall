package com.example.demo.utils.redis;

import java.time.Duration;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.utils.ClassConvert;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class EmailValidateRepository {
	
	private final RedisTemplate<String, Object> redisTemplate;
	private final Long TIME_TO_LIVE = 180000L;
	private static final String PREFIX_FOR_KEY = "EVC: ";
	
	public void setValue(String email, String code) {
		String key = getKey(email);
		redisTemplate.opsForValue().set(key, code, Duration.ofMillis(TIME_TO_LIVE));
	}
	
	public String getKey(String email) {
		return PREFIX_FOR_KEY + email;
	}
	
	public String getValues(String emali) {
		String code  = ClassConvert.castingInstance(
				redisTemplate.opsForValue().get(getKey(emali)), 
				String.class);
		
		return code;
	}
	
}
