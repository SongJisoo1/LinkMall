package com.example.demo.product.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductCloseDto {
	
	private int productId;
	private int buyerId;
	private int sellerId;

}
