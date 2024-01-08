package com.example.demo.product.model;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class Product {
	
	@Setter private int productId;
	@Setter private int memberId;
	private String nickname;
	private int category1;
	private String title;
	private String contents;
	private int price;
	private int viewCnt;
	private int state;
	private int isLiked;
	private int likeCnt;
	private List<ProductImg> imgs;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	@Builder
	private Product(int productId, int memberId, String nickname, int category1, String title,
			String contents, int price, int viewCnt, int state, int isLiked, int likeCnt, List<ProductImg> imgs, LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.productId = productId;
		this.memberId = memberId;
		this.nickname = nickname;
		this.category1 = category1;
		this.title = title;
		this.contents = contents;
		this.price = price;
		this.viewCnt = viewCnt;
		this.state = state;
		this.isLiked = isLiked;
		this.likeCnt = likeCnt;
		this.imgs = imgs;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}	
}
