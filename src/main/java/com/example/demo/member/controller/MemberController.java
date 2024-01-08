package com.example.demo.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.alarm.model.Alarm;
import com.example.demo.alarm.model.Deal;
import com.example.demo.alarm.service.AlarmService;
import com.example.demo.member.dto.EditUserRequestDto;
import com.example.demo.member.dto.LoginRequestDto;
import com.example.demo.member.dto.LoginSessionValue;
import com.example.demo.member.dto.RegisterRequestDto;
import com.example.demo.member.dto.ReviewRequestDto;
import com.example.demo.member.model.Member;
import com.example.demo.member.service.MemberService;
import com.example.demo.product.model.ProductMapper;
import com.example.demo.utils.SessionConstant;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MemberController {
	
	private final MemberService memberService;
	private final AlarmService alarmService;
	private final ProductMapper productMapper;
	
	@GetMapping("/users/register")
	public String registerForm() {
		return "member/registerForm";
	}
	
	@GetMapping("/users/login")
	public String loginForm() {
		return "member/loginForm";
	}
	
	@GetMapping("/users/find-password")
	public String findPwdForm() {
		return "member/findPwdForm";
	}
	
	@GetMapping("/users/edit-info")
	public String editForm(RedirectAttributes rattr, Model model, HttpServletRequest request) {
		
		HttpSession session = request.getSession(false);
		
		if(session == null) {
			rattr.addFlashAttribute("message", "로그인 후 사용할 수 있는 서비스 입니다.");
			return "redirect:/users/login";
		}
		
		LoginSessionValue value = (LoginSessionValue)session.getAttribute(SessionConstant.LOGIN_MEMBER);
		
		if(value == null) {
			rattr.addFlashAttribute("message", "로그인 후 사용할 수 있는 서비스 입니다.");
			return "redirect:/users/login";
		}
		
		Member member = memberService.getMemberByMemberId(value.getMemberId());
		
		model.addAttribute(member); 
		
		return "member/editFrom";
	}
	
	@PostMapping("/users/edit-info")
	public String editMember(@ModelAttribute EditUserRequestDto dto, HttpServletRequest request, RedirectAttributes rattr) {
		
		HttpSession session = request.getSession(false);
		
		Member member = memberService.editMember(dto);
		
		LoginSessionValue value = LoginSessionValue.builder()
				.memberId(member.getMemberId())
				.loginId(member.getLoginId())
				.nickname(member.getNickname())
				.profileImg(member.getProfileImg())
				.build();
		
		session = request.getSession();
		session.setAttribute(SessionConstant.LOGIN_MEMBER, value);
		
		rattr.addFlashAttribute("msg", "회원정보 수정 성공");
		return "redirect:/users/mypage";
		
	}
	
	@PostMapping("/users/register")
	public String register(@ModelAttribute RegisterRequestDto dto) {
		
		memberService.insertMember(dto);
		
		return "member/loginForm";
	}
	
	@PostMapping("/users/login")
	public String login(@ModelAttribute LoginRequestDto dto, RedirectAttributes rattr, HttpServletRequest request) {
		
		Member checkBan = memberService.getMemberByLoginId(dto.getLoginId());
		
		// 계정 잠금 여부 확인
		if(checkBan.getBan() == 1) {
			rattr.addFlashAttribute("message", "VAN_USER");
			return "redirect:/users/login";
		}
		
		// 로그인
		Member existMember = memberService.login(dto);
		
		// 아이디, 비밀번호 틀렸을 경우 
		if (existMember == null) {
			rattr.addFlashAttribute("message", "INCORRECT_ACC");
			return "redirect:/users/login";
		}
		
		// 세션에 넣을 값
		LoginSessionValue value = LoginSessionValue.builder()
									.memberId(existMember.getMemberId())
									.loginId(existMember.getLoginId())
									.nickname(existMember.getNickname())
									.profileImg(existMember.getProfileImg())
									.build();
		
		HttpSession session = request.getSession();
		session.setAttribute(SessionConstant.LOGIN_MEMBER, value);
		
		return "redirect:/products";
	}
	
	@PostMapping("/users/logout")
	@ResponseBody
	public void logout(HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		session.invalidate();
	}
	
	@GetMapping("/users/help")
	public @ResponseBody ResponseEntity<Member> findMember(@RequestParam("id") String loginId) {
		
		Member member = memberService.getMemberByLoginId(loginId);
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(member);
	}
	
	@PostMapping("/users/review")
	public void addReview(@RequestBody ReviewRequestDto dto) {
		System.out.println(dto);
		Deal deal = alarmService.findDealById(dto.getAlarmId());
		System.out.println(deal);
		deal.setIsRead(1);
		alarmService.isReadDeal(deal);
		
		Member member = memberService.getMemberByMemberId(dto.getSellerId());
		
		member.setRate(dto.getRank());
		
		memberService.setReview(member);
	}
		
	@GetMapping("/user/check-id")
	public @ResponseBody ResponseEntity<Boolean> checkDuplicatedId(
			@RequestParam("id") String logindId
	) {
		System.out.println(logindId);
		boolean result = false;
		Member member = memberService.getMemberByLoginId(logindId);
		
		if(member == null) result = true;
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(result);
				
	}
	
	@GetMapping("/user/check-nick")
	public @ResponseBody ResponseEntity<Boolean> checkDuplicatedNickname(
			@RequestParam("nickname") String nickname
	) {
		
		boolean result = false;
		Member member = memberService.getMemberByNickname(nickname);
		
		if(member == null) result = true;
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(result);
				
	}

}
