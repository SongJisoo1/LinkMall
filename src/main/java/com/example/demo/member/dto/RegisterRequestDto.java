package com.example.demo.member.dto;

import com.example.demo.member.model.Member;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Data
@ToString
public class RegisterRequestDto {
	
	private String loginId;
	private String nickname;
	private String password;
	private String email;
	private String postAddress;
	private String roadAddress;
	private String detailAddress;
	
	@Builder
	private RegisterRequestDto(String loginId, String nickname, String password, String email,
			String postAddress, String roadAddress, String detailAddress) {
		
		this.loginId = loginId;
		this.nickname = nickname;
		this.password = password;
		this.email = email;
		this.postAddress = postAddress;
		this.roadAddress = roadAddress;
		this.detailAddress = detailAddress;
	}
	
	public static Member toModel(RegisterRequestDto dto) {
		return Member.builder()
				.loginId(dto.getLoginId())
				.nickname(dto.getNickname())
				.password(dto.getPassword())
				.email(dto.getEmail())
				.postAddress(dto.getPostAddress())
				.roadAddress(dto.getRoadAddress())
				.detailAddress(dto.getDetailAddress())
				.build();
	}
}
