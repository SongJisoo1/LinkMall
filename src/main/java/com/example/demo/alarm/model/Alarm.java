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
public class Alarm {
	
	private int alarmId;
	private int memberId;
	private int productId;
	private String category;
	private String title;
	private String alarmType;
	private int isRead;
	private String href;
	private int buyerId;
	private int sellerId;
	private String buyerNickname;
	private String sellerNickname;
	private LocalDateTime createdAt;
	
	@Builder
	private Alarm(int alarmId, int memberId, int productId, String category, String title, String alarmType, int isRead,
			String href, int buyerId, int sellerId, String buyerNickname, String sellerNickname,
			LocalDateTime createdAt) {
		
		this.alarmId = alarmId;
		this.memberId = memberId;
		this.productId = productId;
		this.category = category;
		this.title = title;
		this.alarmType = alarmType;
		this.isRead = isRead;
		this.href = href;
		this.buyerId = buyerId;
		this.sellerId = sellerId;
		this.buyerNickname = buyerNickname;
		this.sellerNickname = sellerNickname;
		this.createdAt = createdAt;
	}
	
}
