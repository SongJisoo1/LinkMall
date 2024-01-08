package com.example.demo.utils.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class PagingResponse<T> {
	
	private List<T> list = new ArrayList<>();
	private Pagination pagination;
}
