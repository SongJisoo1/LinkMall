package com.example.demo.alarm.model;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Deal {
	
	private int dealId;
	private int productId;
	private String category;
	private String title;
	private int buyerId;
	private int sellerId;
	private String buyerNickname;
	private String sellerNickname;
	private int isRead;
	private LocalDateTime createdAt;
	
	
	@Builder
	private Deal(int dealId, int productId, String category, String title, int buyerId, int sellerId,
			String buyerNickname, String sellerNickname, int isRead, LocalDateTime createdAt) {
		
		this.dealId = dealId;
		this.productId = productId;
		this.category = category;
		this.title = title;
		this.buyerId = buyerId;
		this.sellerId = sellerId;
		this.buyerNickname = buyerNickname;
		this.sellerNickname = sellerNickname;
		this.isRead = isRead;
		this.createdAt = createdAt;
	}
	
	public static Alarm toAlarm(Deal deal) {
		return Alarm.builder()
				.alarmId(deal.getDealId())
				.productId(deal.getProductId())
				.category(deal.getCategory())
				.title(deal.getTitle())
				.alarmType("DEAL")
				.buyerId(deal.getBuyerId())
				.buyerNickname(deal.getBuyerNickname())
				.sellerId(deal.getSellerId())
				.sellerNickname(deal.getSellerNickname())
				.createdAt(deal.getCreatedAt())
				.build();
				
	}
}
