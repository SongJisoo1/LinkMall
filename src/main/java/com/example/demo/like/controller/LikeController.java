package com.example.demo.like.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.like.model.Like;
import com.example.demo.like.service.LikeService;
import com.example.demo.member.dto.LoginSessionValue;
import com.example.demo.utils.SessionConstant;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class LikeController {

	private final LikeService likeService;
	
	@PostMapping("/products/{product_id}/likes")
	public ResponseEntity productLike(
			@PathVariable("product_id") int productId,
			HttpServletRequest req
	) {
		
		HttpSession session = req.getSession(false);
		
		if(session == null ||
		   (LoginSessionValue)session.getAttribute(SessionConstant.LOGIN_MEMBER) == null
		) {
			return ResponseEntity
					.status(HttpStatus.UNAUTHORIZED)
					.body(false);
		}
		
		LoginSessionValue value = (LoginSessionValue)session.getAttribute(SessionConstant.LOGIN_MEMBER);
		
		Like like = Like.builder()
				.productId(productId)
				.memberId(value.getMemberId())
				.build();
		
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(likeService.insertOrDeleteLike(like));
		
	}
}
