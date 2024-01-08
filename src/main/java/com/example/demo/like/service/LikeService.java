package com.example.demo.like.service;

import org.springframework.stereotype.Service;

import com.example.demo.like.dto.LikeResponseDto;
import com.example.demo.like.model.Like;
import com.example.demo.like.model.LikeMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LikeService {
	
	private final LikeMapper mapper;
	
	public LikeResponseDto insertOrDeleteLike(Like like) {
		//좋아요 등록
		
		if(mapper.findByMemberIdAndProductId(like).isPresent()) {
			// null이 아니면 이미 좋아요가 존재, 좋아요 삭제
			mapper.deleteLike(like);
		} else {
			// null이면 좋아요가 존재하지 않음, 좋아요 등록
			mapper.insertLike(like);
		}
		
		// 게시물의 전체 좋아요 갯수 
		int totalLikeCount = mapper.totalLikeCount(like.getProductId());
		
		System.out.println(totalLikeCount);
		
		return LikeResponseDto.of(like.getProductId(), totalLikeCount);
		
	}
}
