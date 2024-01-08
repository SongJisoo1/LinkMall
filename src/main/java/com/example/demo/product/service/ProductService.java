package com.example.demo.product.service;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.alarm.service.AlarmService;
import com.example.demo.product.dto.ProductCloseDto;
import com.example.demo.product.dto.ProductDeleteDto;
import com.example.demo.product.dto.ProductEditDto;
import com.example.demo.product.dto.ProductRegisterDto;
import com.example.demo.product.dto.ProductResponseDto;
import com.example.demo.product.model.Product;
import com.example.demo.product.model.ProductMapper;
import com.example.demo.utils.dto.Pagination;
import com.example.demo.utils.dto.PagingResponse;
import com.example.demo.utils.dto.SearchDto;
import com.example.demo.utils.s3.S3UploadService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductService {
	
	private final S3UploadService s3;
	private final AlarmService alarmService;
	private final ProductMapper mapper;
	
	public void insertProduct(ProductRegisterDto dto, int MemberId) {
		
		Product product = ProductRegisterDto.toModel(dto);
		product.setMemberId(MemberId);
		
		mapper.insertProduct(product);
		List<String> imgs = s3.uploadFiles(dto.getImgs());
		
		imgs.forEach(elem -> {
			mapper.insertImgs(elem, product.getProductId());
		});
		
	}
	
	public PagingResponse<ProductResponseDto> getAllProducts(SearchDto params, int memberId) {
		
		System.out.println(params);
		
		int count = mapper.countAllProducts(params);
		
		if(count < 1) {
			return new PagingResponse<>(Collections.emptyList(), null);
		}
		
		Pagination pagination = new Pagination(count, params);
		params.setPagination(pagination);
		
		List<ProductResponseDto> list = mapper.findAllProducts(params, memberId).stream()
				.map(ProductResponseDto::fromModel)
				.toList();
		
		return new PagingResponse<>(list, pagination);
	}
	
	public PagingResponse<ProductResponseDto> getFilteredProducts(SearchDto params, int memberId) {
		System.out.println(params);
		
		int count = mapper.countAllProducts(params);
		
		if(count < 1) {
			return new PagingResponse<>(Collections.emptyList(), null);
		}
		
		Pagination pagination = new Pagination(count, params);
		params.setPagination(pagination);
		
		List<ProductResponseDto> list = mapper.findAllProducts(params, memberId).stream()
				.map(ProductResponseDto::fromModel)
				.toList();
		
		return new PagingResponse<>(list, pagination);
	}
	
	public Product getProductById(int productId, int memberId) {
		return mapper.findProductByProductId(productId, memberId)
				.orElse(null);
	}
	
	public void updateViewCnt(int productId) {
		mapper.incrementViewCnt(productId);
	}
	
	public void updateState(ProductEditDto dto) {
		mapper.updateState(ProductEditDto.toModel(dto));
		
		List<String> likedMembers = alarmService.getAllLikedMember(dto.getProductId());
		
		System.out.println(likedMembers);
		
		if(dto.getState() == 0) {
			alarmService.createdAlarm("SALE", dto.getProductId(), likedMembers);
		} else if(dto.getState() == 1) {
			alarmService.createdAlarm("RESERVATION", dto.getProductId(), likedMembers);
		} else if(dto.getState() == 2) {
			alarmService.createdAlarm("SOLD", dto.getProductId(), likedMembers);
		}
	}
	
	public void deleteProduct(ProductDeleteDto dto) {
		mapper.deleteProduct(dto.getProductId());
	}
	
	public void productsClose(ProductCloseDto dto) {
		
		ProductEditDto editDto = new ProductEditDto();
		editDto.setProductId(dto.getProductId());
		editDto.setState(2);
		updateState(editDto);
		
		alarmService.updateProductClose(dto);
	}
	
}
