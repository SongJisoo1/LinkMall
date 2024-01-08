package com.example.demo.member.service;

import java.util.Collections;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.member.dto.EditUserRequestDto;
import com.example.demo.member.dto.LoginRequestDto;
import com.example.demo.member.dto.RegisterRequestDto;
import com.example.demo.member.dto.ReviewRequestDto;
import com.example.demo.member.model.Member;
import com.example.demo.member.model.MemberMapper;
import com.example.demo.product.dto.ProductResponseDto;
import com.example.demo.product.model.ProductMapper;
import com.example.demo.utils.AverageCalculator;
import com.example.demo.utils.dto.Pagination;
import com.example.demo.utils.dto.PagingResponse;
import com.example.demo.utils.dto.SearchDto;
import com.example.demo.utils.redis.LoginFailedRepository;
import com.example.demo.utils.s3.S3UploadService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberService {
	
	private final Integer INIT_LOGIN_TRIAL_CNT = 1;
	private final Long MAX_LOGIN_TRIAL_CNT = 10L;
	private final String DEFAULT_PROFILE = "default_user_profile.png";

	private final MemberMapper memberMapper;
	private final ProductMapper productMapper;
	private final LoginFailedRepository loginFailedRepository;
	private final PasswordEncoder passwordEncoder;
	private final S3UploadService s3UploadService;
	
	public void insertMember(RegisterRequestDto dto) {
		Member member = RegisterRequestDto.toModel(dto);
		
		// 비밀번호를 단방향 해싱한다. 
		member.setPassword(passwordEncoder.encode(member.getPassword()));
		
		// 최초 회원가입시는 기본 프로필 이미지 고정, 회원정보 수정에서 수정할 수 있도록
		member.setProfileImg(DEFAULT_PROFILE);
		
		memberMapper.insertMember(member);
	}
	
	public Member login(LoginRequestDto dto) {
		return memberMapper.findMemberByLoginId(dto.getLoginId())
				.filter(m -> validatePassword(dto, m))
				.orElse(null);
	}
	
	public boolean validatePassword(LoginRequestDto dto, Member member) {
		if(!passwordEncoder.matches(dto.getPassword(), member.getPassword())) {
			// 비밀번호 일치하지 않을 시 레디스에 로그인 시도횟수 증가 후 false 반환
			countLoginFailed(member);
			return false;
		}
		// 비밀번호 일치 시 true 반환
		return true;
	}
	
	public void countLoginFailed(Member member) {
		// 총 로그인 시도 횟수 
		Long attemptCount  = incrementFailedCount(member);
		// 10회를 초과 했을 시 
		if(attemptCount >= MAX_LOGIN_TRIAL_CNT) {
			member.setBan(1);
			memberMapper.lockMemberAccount(member);
			loginFailedRepository.delete(member.getLoginId());
		}
	}
	
	public Long incrementFailedCount(Member member) {
		// 아이디를 키값으로 레디스에서 로그인 시도 횟수 가져옴
		Integer failedCount = loginFailedRepository.getValues(member.getLoginId());
		// 시도 횟수가 없을 시 
		if(failedCount == null) {
			loginFailedRepository.setValue(member.getLoginId(), INIT_LOGIN_TRIAL_CNT);
			return 1L;
		}
		
		// 있을시 1회 증가 
		return loginFailedRepository.increment(member.getLoginId());
	}
	
	public Double getMemberRate(int memberId) {
		Member existMember = getMemberByMemberId(memberId);
		Double average = AverageCalculator.getAverage(existMember.getRate(), existMember.getRateCnt());
	    return average;
	}
	
	public PagingResponse<ProductResponseDto> getAllPostsByMemberId(int memberId, SearchDto params) {
		
		// 유저가 작성한 게시물의 총 갯수를 구한다.
		int count = productMapper.countByMemberPosts(memberId);
		
		if (count < 1) {
			return new PagingResponse<>(Collections.emptyList(), null);
		}
		
		Pagination pagination = new Pagination(count, params);
	
		List<ProductResponseDto> list = productMapper.fintPostsByMemberId(memberId, params.getPagination().getLimitStart(), params.getRecordSize())
											.stream()
											.map(ProductResponseDto::fromModel)
											.toList();
		
		return new PagingResponse<>(list, pagination);

	}
	
	public PagingResponse<ProductResponseDto> getAllLikeListByMemberId(int memberId, SearchDto params) {
		
		
		int count = productMapper.countByMemberLikeList(memberId);
		
		if (count < 1) {
			return new PagingResponse<>(Collections.emptyList(), null);
		}
		
		Pagination pagination = new Pagination(count, params);
	
		List<ProductResponseDto> list = productMapper.findLikeListByMemberId(memberId, params.getPagination().getLimitStart(), params.getRecordSize())
											.stream()
											.map(ProductResponseDto::fromModel)
											.toList();
		
		return new PagingResponse<>(list, pagination);

	}
	
	public void setReview(Member member) {
		memberMapper.updateReview(member);
	}
	
	public void resetPassword(Member member) {
		String encode = passwordEncoder.encode(member.getPassword());
		member.setPassword(encode);
		memberMapper.resetPassword(member);
	}
	
	public Member editMember(EditUserRequestDto dto) {
		
		List<String> fileName = s3UploadService.uploadFiles(dto.getImgs());
		
		Member member = EditUserRequestDto.toModel(dto);
		
		member.setProfileImg(fileName.get(0));
		
		memberMapper.updateMember(member);
	
		return member;
	}
	
	public Member getMemberByMemberId(int memberId) {
		return memberMapper.findMemberByMemberId(memberId)
				.orElse(null);
	}
	
	public Member getMemberByLoginId(String loginId) {
		return memberMapper.findMemberByLoginId(loginId)
				.orElse(null);
	}
	
	public Member getMemberByNickname(String nickname) {
		return memberMapper.findMemberByNickname(nickname)
				.orElse(null);
	}
	
	public Member getMemberByEmail(String email) {
		return memberMapper.findMemberByEmail(email);
	}
}
