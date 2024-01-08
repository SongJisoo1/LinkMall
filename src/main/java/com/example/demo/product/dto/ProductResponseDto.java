package com.example.demo.product.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.product.model.Product;
import com.example.demo.product.model.ProductImg;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class ProductResponseDto {
	
	private int productId;
	private int memberId;
	private String nickname;
	private String title;
	private String contents;
	private int price;
	private int viewCnt;
	private int state;
	private int isLiked;
	private int likeCnt;
	private List<ProductImg> imgs;
	private LocalDateTime createdAt;
	
	@Builder
	private ProductResponseDto(int productId, int memberId, String nickname, String title, String contents, int price,
			int viewCnt, int state, int isLiked, int likeCnt, List<ProductImg> imgs, LocalDateTime createdAt) {
		
		this.productId = productId;
		this.memberId = memberId;
		this.nickname = nickname;
		this.title = title;
		this.contents = contents;
		this.price = price;
		this.viewCnt = viewCnt;
		this.state = state;
		this.isLiked = isLiked;
		this.likeCnt = likeCnt;
		this.imgs = imgs;
		this.createdAt = createdAt;
	}
	
	public static ProductResponseDto fromModel(Product product) {
		return ProductResponseDto.builder()
				.productId(product.getProductId())
				.memberId(product.getMemberId())
				.nickname(product.getNickname())
				.title(product.getTitle())
				.contents(product.getContents())
				.price(product.getPrice())
				.viewCnt(product.getViewCnt())
				.state(product.getState())
				.isLiked(product.getIsLiked())
				.likeCnt(product.getLikeCnt())
				.imgs(product.getImgs())
				.createdAt(product.getCreatedAt())
				.build();
	}
}
