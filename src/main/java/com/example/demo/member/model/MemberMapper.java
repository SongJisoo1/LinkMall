			package com.example.demo.member.model;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.member.dto.ReviewRequestDto;

@Mapper
@Repository
public interface MemberMapper {
	void insertMember(Member member);
	void updateMember(Member member);
	Optional<Member> findMemberByMemberId(int memberId);
	Optional<Member> findMemberByLoginId(String loginId);
	Optional<Member> findMemberByNickname(String nickname);
	Member findMemberByEmail(String email);
	Optional<Member> findMemberByNameAndEamil(@Param("email") String email, @Param("name") String name);
	void lockMemberAccount(Member member);
	void updateReview(Member member);
	void resetPassword(Member member);
}
