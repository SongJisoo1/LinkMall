package com.example.demo.member.dto;

import com.example.demo.member.model.Member;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class LoginSessionValue {
	
	private int memberId;
	private String loginId;
	private String nickname;
	private String profileImg;
	
	@Builder
	private LoginSessionValue(int memberId, String loginId, String nickname, String profileImg) {
		this.memberId = memberId;
		this.loginId = loginId;
		this.nickname = nickname;
		this.profileImg = profileImg;
	}

	public static LoginSessionValue fromModel(Member member) {
		return LoginSessionValue.builder()
				.memberId(member.getMemberId())
				.loginId(member.getLoginId())
				.nickname(member.getNickname())
				.profileImg(member.getProfileImg())
				.build();
	}

}
