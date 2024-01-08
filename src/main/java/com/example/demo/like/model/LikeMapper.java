package com.example.demo.like.model;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface LikeMapper {
	Optional<Like> findByMemberIdAndProductId(Like like);
	int totalLikeCount(int productId);
	void insertLike(Like like);
	void deleteLike(Like like);
}
