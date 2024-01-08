package com.example.demo.member.dto;

import com.example.demo.member.model.Member;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class LoginRequestDto {
	
	private String loginId;
	private String password;
	
	@Builder
	private LoginRequestDto(String loginId, String password) {
		this.loginId = loginId;
		this.password = password;
	}
	
	public static Member toModel(RegisterRequestDto dto) {
		return Member.builder()
				.loginId(dto.getLoginId())
				.password(dto.getPassword())
				.build();
	}
}
