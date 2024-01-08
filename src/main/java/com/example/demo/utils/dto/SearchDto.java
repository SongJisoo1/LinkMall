package com.example.demo.utils.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SearchDto {
	
	private int page; // 현재 페이지 번호
	private int recordSize; // 페이지 당 출력할 데이터 갯수 
	private int pageSize; // 출력할 페이지 사이즈
	private String searchType;
	private String keyword; // 검색 키워드
	private String title;
	private String seller;
	private int lowPrice;
	private int highPrice;
	private int category;
	private Pagination pagination;
	
	public SearchDto() {
		this.page = 1;
		this.recordSize = 12;
		this.pageSize = 5;
	}
	
	public int getOffset() {
		return (page - 1) * recordSize;
	}
}
