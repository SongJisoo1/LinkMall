package com.example.demo.like.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LikeResponseDto {
	
	private int productId;
	private int totalLikeCnt;
	
	public static LikeResponseDto of (int productId, int totalLikeCnt) {
		return new LikeResponseDto(productId, totalLikeCnt);
	}
}
