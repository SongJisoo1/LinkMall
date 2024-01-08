package com.example.demo.alarm.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.example.demo.alarm.model.Alarm;
import com.example.demo.alarm.model.AlarmMapper;
import com.example.demo.alarm.model.Deal;
import com.example.demo.product.dto.ProductCloseDto;
import com.example.demo.utils.dto.Pagination;
import com.example.demo.utils.dto.PagingResponse;
import com.example.demo.utils.dto.SearchDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AlarmService {

	private final AlarmMapper mapper;
	
	public PagingResponse<Alarm> getAllAlarms(int memberId, SearchDto params) {
		
		int count = mapper.countAlarmsByMemberId(memberId);
		
		if(count < 1) {
			return new PagingResponse<>(Collections.emptyList(), null);
		}
		
		Pagination pagination = new Pagination(count, params);
		
		List<Alarm> alarmList = mapper.findAllAlarm(memberId);
		
		List<Alarm> dealList = mapper.findAllDeal(memberId).stream()
			    .map(Deal::toAlarm) // 이 부분에서 Deal 객체를 Alarm 객체로 변환
			    .collect(Collectors.toList());
		
		List<Alarm> list = Stream.concat(alarmList.stream(), dealList.stream())
							        .sorted(Comparator.comparing(Alarm::getCreatedAt, Comparator.reverseOrder()))
							        .toList();
		
		System.out.println(list);
		
		return new PagingResponse<>(list, pagination);
	}
	
	public List<String> getAllLikedMember(int productId) {
		return mapper.findAllLikedMember(productId);
	}
	
	public void createdAlarm(String type, int productId, List<String> likedMembers) {
		
		if(likedMembers.isEmpty()) return;
		
		Map<String, Object> params = new HashMap<>();
		
		params.put("type", type);
		params.put("productId", productId);
		params.put("href", "/products/test2?product_id=" + productId);
		params.put("likedMembers", likedMembers);
		
		mapper.insertAlarm(params);
	}
	
	public Deal findDealById(int dealId) {
		return mapper.findDealById(dealId);
	}
	
	public void deleteAlarm(int alarmId) {
		mapper.deleteAlarm(alarmId);
	}
	
	public void updateReadstate(Alarm alarm) {
		mapper.updateReadstate(alarm);
	}
	
	public void isReadDeal(Deal deal) {
		mapper.updateDealReadState(deal);
	}
	
	public void updateProductClose(ProductCloseDto dto) {
		
		Map<String, Object> params = new HashMap<>();
		
		params.put("productId", dto.getProductId());
		params.put("buyerId", dto.getBuyerId());
		params.put("sellerId", dto.getSellerId());
		
		mapper.updateProductClose(params);
	}
}
