package com.example.demo.member.dto;

import com.example.demo.member.model.Member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ReviewRequestDto {
	
	private int rank;
	private int productId;
	private int buyerId;
	private int sellerId;
	private int alarmId;
	
	@Builder
	private ReviewRequestDto(int rank, int productId, int buyerId, int sellerId, int alarmId) {
		this.rank = rank;
		this.productId = productId;
		this.buyerId = buyerId;
		this.sellerId = sellerId;
		this.alarmId = alarmId;
	}
	
	public static Member toModel(int rank) {
		return Member.builder()
				.rate(rank)
				.build();
	}
}
