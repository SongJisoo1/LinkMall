package com.example.demo.like.model;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Like {
	
	private int likeId;
	private int productId;
	private int memberId;
	
	@Builder
	private Like(int likeId, int productId, int memberId) {
		this.likeId = likeId;
		this.productId = productId;
		this.memberId = memberId;
	}
}
