package com.example.demo.member.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.member.model.Member;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class EditUserRequestDto {
	
	private int memberId;
	private String loginId;
	private String nickname;
	private String email;
	private String postAddress;
	private String roadAddress;
	private String detailAddress;
	private List<MultipartFile> imgs;
	
	@Builder
	private EditUserRequestDto(int memberId, String loginId, String nickname, String email, String postAddress, String roadAddress,
			String detailAddress, List<MultipartFile> imgs) {
		super();
		this.memberId = memberId;
		this.loginId = loginId;
		this.nickname = nickname;
		this.email = email;
		this.postAddress = postAddress;
		this.roadAddress = roadAddress;
		this.detailAddress = detailAddress;
		this.imgs = imgs;
	}
	
	public static Member toModel(EditUserRequestDto dto) {
		return Member.builder()
				.memberId(dto.getMemberId())
				.loginId(dto.getLoginId())
				.nickname(dto.getNickname())
				.postAddress(dto.getPostAddress())
				.roadAddress(dto.getRoadAddress())
				.detailAddress(dto.getDetailAddress())
				.build();
	}

}
