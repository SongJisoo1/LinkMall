package com.example.demo.mail.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.mail.service.MailService;
import com.example.demo.member.model.Member;
import com.example.demo.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MailController {
	
	private final MailService mailService;
	private final MemberService memberService;
	
	@PostMapping("/mail/send-code")
	@ResponseBody
	public ResponseEntity<Boolean> mailConfirm(@RequestParam("email") String email) throws Exception {

	   mailService.sendSimpleMessage(email);
	   
	   return ResponseEntity
			   .status(HttpStatus.OK)
			   .body(true);
	}
	
	@GetMapping("/mail/check-code")
	@ResponseBody
	public ResponseEntity<Boolean> verifiedCode(
			@RequestParam("email")String email, 
			@RequestParam("code")String code,
			@RequestParam("type")String type) {
		
		boolean result = mailService.verifiedCode(email, code);
		
		if(result == true && type.equals("re")) {
			Member member = memberService.getMemberByEmail(email);
			String newPassword = mailService.createKey();
			
			member.setPassword(newPassword);
			
			memberService.resetPassword(member);
			
			mailService.sendPassword(email, newPassword);
		}
		
		return ResponseEntity
				   .status(HttpStatus.OK)
				   .body(result);
	}
	
	@PostMapping("/mail/verified")
	@ResponseBody
	public ResponseEntity<Boolean> verifiedEmail(
			@RequestParam("email") String email, 
			@RequestParam("id") String loginId
	) {
		boolean result = mailService.verifiedEmailByLoginId(email, loginId);
		
		return ResponseEntity
				   .status(HttpStatus.OK)
				   .body(result);
	}

}
