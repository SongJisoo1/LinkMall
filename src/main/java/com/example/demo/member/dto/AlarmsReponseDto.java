//package com.example.demo.member.dto;
//
//import java.time.LocalDateTime;
//
//import com.example.demo.alarm.model.Alarm;
//
//import lombok.Builder;
//import lombok.Getter;
//import lombok.Setter;
//
//@Getter
//public class AlarmsReponseDto {
//	
//	private int memberId;
//	private int productId;
//	private String title;
//	private String alarmType;
//	@Setter private int isRead;
//	private LocalDateTime createdAt;
//	
//	@Builder
//	private AlarmsReponseDto(int memberId, int productId, String title, String alarmType, int isRead,
//			LocalDateTime createdAt) {
//		this.memberId = memberId;
//		this.productId = productId;
//		this.title = title;
//		this.alarmType = alarmType;
//		this.isRead = isRead;
//		this.createdAt = createdAt;
//	}
//	
//	
//	public static AlarmsReponseDto fromModel(Alarm alarm) {
//		return AlarmsReponseDto.builder()
//				.memberId(alarm.getMemberId())
//				.productId(alarm.getProductId())
//				.title(alarm.getTitle())
//				.createdAt(alarm.getCreatedAt())
//				.build();
//	}
//}
