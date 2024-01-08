package com.example.demo.member.model;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Member {
	
	private int memberId;
	private String loginId;
	private String password;
	private String nickname;
	private String email;
	private String postAddress;
	private String roadAddress;
	private String detailAddress;
	private String profileImg;
	private double rate;
	private int rateCnt;
	private String role;
	private int ban;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	@Builder
	private Member(int memberId, String loginId, String password, String nickname, String email,
			String postAddress, String roadAddress, String detailAddress, double rate, String profileImg, int rateCnt, String role, int ban,
			LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.memberId = memberId;
		this.loginId = loginId;
		this.password = password;
		this.nickname = nickname;
		this.email = email;
		this.postAddress = postAddress;
		this.roadAddress = roadAddress;
		this.detailAddress = detailAddress;
		this.rate = rate;
		this.profileImg = profileImg;
		this.rateCnt = rateCnt;
		this.role = role;
		this.ban = ban;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
	public void setRate(int rate) {
		this.rate += rate;
	}
}
