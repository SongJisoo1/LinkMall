package com.example.demo.product.model;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.utils.dto.SearchDto;

@Repository
@Mapper
public interface ProductMapper {
	int insertProduct(Product product);
	void insertImgs(@Param("imgName") String imgName, @Param("productId") int productId);
	List<Product> fintPostsByMemberId(@Param("memberId") int memberId, @Param("limitStart") int limitStart, @Param("recordSize") int recordSize);
	List<Product> findLikeListByMemberId(@Param("memberId") int memberId, @Param("limitStart") int limitStart, @Param("recordSize") int recordSize);
	List<Product> findAllProducts(@Param("params") SearchDto params, @Param("memberId") int memberId);
	Optional<Product> findProductByProductId(@Param("productId") int productId, @Param("memberId") int memberId);
	void incrementViewCnt(int productId);
	void updateState(Product product);
	void deleteProduct(int productId);
	int countAllProducts(@Param("params") SearchDto params);
	int countByMemberPosts(int memberId);
	int countByMemberLikeList(int memberId);
}
