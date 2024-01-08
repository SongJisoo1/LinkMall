package com.example.demo.product.dto;

import com.example.demo.product.model.Product;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductEditDto {
	
	private int productId;
	private int state;
	
	public static Product toModel(ProductEditDto dto) {
		return Product.builder()
				.productId(dto.getProductId())
				.state(dto.getState())
				.build();
	}

}
