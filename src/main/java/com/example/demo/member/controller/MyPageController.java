package com.example.demo.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.alarm.model.Alarm;
import com.example.demo.alarm.service.AlarmService;
import com.example.demo.member.dto.LoginSessionValue;
import com.example.demo.member.model.Member;
import com.example.demo.member.service.MemberService;
import com.example.demo.product.dto.ProductResponseDto;
import com.example.demo.utils.SessionConstant;
import com.example.demo.utils.dto.PagingResponse;
import com.example.demo.utils.dto.SearchDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MyPageController {
	
	private final MemberService memberService;
	private final AlarmService alarmService;
	
	@GetMapping("/users/mypage")
	public String getMyPage(HttpServletRequest request, Model model, RedirectAttributes rattr) {
		
		HttpSession session = request.getSession(false);
		
		if(session == null || session.getAttribute(SessionConstant.LOGIN_MEMBER) == null ) {
			rattr.addFlashAttribute("msg", "로그인이 필요한 서비스입니다.");
			return "redirect:/users/login";
		}
		
		LoginSessionValue value = (LoginSessionValue)session.getAttribute(SessionConstant.LOGIN_MEMBER);
		
		Member member = memberService.getMemberByMemberId(value.getMemberId());
		
		Double memberRateAverage = memberService.getMemberRate(value.getMemberId());
		
		model.addAttribute("member", member);
		model.addAttribute("rate", memberRateAverage);
		
		return "member/mypage";
		
	}
	
	@GetMapping("/users/alarm")
	@ResponseBody
	public PagingResponse<Alarm> getUsersAlarm(SearchDto params, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		LoginSessionValue value = (LoginSessionValue)session.getAttribute(SessionConstant.LOGIN_MEMBER);
		
		PagingResponse<Alarm> list =  alarmService.getAllAlarms(value.getMemberId(), params);
		
		System.out.println("list : " + list);
		
		return list;
	}
	
	@GetMapping("/users/{member_id}/posts")
	@ResponseBody
	public PagingResponse<ProductResponseDto> getUserPosts(
			@PathVariable("member_id") int memberId,
			SearchDto params,
			RedirectAttributes rattr
	) {
		
		Member member = memberService.getMemberByMemberId(memberId);
		
		return memberService.getAllPostsByMemberId(memberId, params);
	}
	
	@GetMapping("/users/{member_id}/like-list")
	@ResponseBody
	public PagingResponse<ProductResponseDto> getUserLikeList(
			@PathVariable("member_id") int memberId,
			SearchDto params,
			RedirectAttributes rattr
	) {
		
		Member member = memberService.getMemberByMemberId(memberId);
		
		return memberService.getAllLikeListByMemberId(memberId, params);
	}

}
