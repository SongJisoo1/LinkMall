package com.example.demo.product.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.product.model.Product;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class ProductRegisterDto {
	
	private String title;
	private int price;
	private String contents;
	private int category;
	private List<MultipartFile> imgs;
	
	@Builder
	private ProductRegisterDto(String title, int price, String contents, int category, List<MultipartFile> imgs) {
		this.title = title;
		this.price = price;
		this.contents = contents;
		this.category = category;
		this.imgs = imgs;
	}
	  
	public static Product toModel(ProductRegisterDto dto) {
		return Product.builder()
				.title(dto.getTitle())
				.price(Integer.valueOf(dto.getPrice()))
				.contents(dto.getContents())
				.category1(dto.getCategory())
				.build();
	}
}
