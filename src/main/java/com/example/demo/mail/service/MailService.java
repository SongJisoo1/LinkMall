package com.example.demo.mail.service;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.demo.member.model.Member;
import com.example.demo.member.service.MemberService;
import com.example.demo.utils.redis.EmailValidateRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MailService {
	
	private String ePw;
	
	private final JavaMailSender mailSender;
	private final MemberService memberService;
	private final EmailValidateRepository emailValidateRepository;
	
	public MimeMessage createMessage(String to, String type) throws MessagingException, UnsupportedEncodingException  {
		
		MimeMessage message = mailSender.createMimeMessage();
		
		message.addRecipients(RecipientType.TO, to); // 메일을 보내는 대상
		message.setSubject("LinkMall 이메일 인증번호 입니다."); // 메일 제목
		
		String msg = "";
		
		msg += "<div style='margin:100px;'>";
		msg += "<h1> 안녕하세요</h1>";
		msg += "<h1> 중고 거래 플랫폼 LinkMall 입니다.</h1>";
		msg += "<br>";
		
		msg += "<p>아래 코드를 입력해주세요<p>";
		
		
		msg += "<br>";
		msg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
		
		msg += "<h3 style='color:blue;'>이메일 인증 코드입니다.</h3>";
			
		msg += "<div style='font-size:130%'>";
		msg += "CODE : <strong>";
		msg += ePw + "</strong><div><br/> "; // 메일에 인증번호 넣기
		msg += "</div>";
		
		message.setText(msg, "utf-8", "html");// 내용, charset 타입, subtype
		// 보내는 사람의 이메일 주소, 보내는 사람 이름
		message.setFrom(new InternetAddress("supersonic97@naver.com", "LinkMall_Admin"));// 보내는 사람
	
		return message;
	}
	
	public MimeMessage createPassword(String to, String type, String password) throws MessagingException, UnsupportedEncodingException  {
		
		MimeMessage message = mailSender.createMimeMessage();
		
		message.addRecipients(RecipientType.TO, to); // 메일을 보내는 대상
		message.setSubject("LinkMall 이메일 인증번호 입니다."); // 메일 제목
		
		String msg = "";
		
		msg += "<div style='margin:100px;'>";
		msg += "<h1> 안녕하세요</h1>";
		msg += "<h1> 중고 거래 플랫폼 LinkMall 입니다.</h1>";
		msg += "<br>";
		
		if(type.equals("code")) {
			msg += "<p>아래 코드를 입력해주세요<p>";
		} else if (type.equals("pwd")) {
			msg += "<p>임시 비밀번호 입니다.</p>";
		}
		
		msg += "<br>";
		msg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
		
		
		if(type.equals("code")) {
			msg += "<h3 style='color:blue;'>이메일 인증 코드입니다.</h3>";
		} else if (type.equals("pwd")) {
			msg += "<p>임시 비밀번호 입니다.</p>";
		}
			
		msg += "<div style='font-size:130%'>";
		msg += "CODE : <strong>";
		msg += password + "</strong><div><br/> "; // 메일에 인증번호 넣기
		msg += "</div>";
		message.setText(msg, "utf-8", "html");// 내용, charset 타입, subtype
		// 보내는 사람의 이메일 주소, 보내는 사람 이름
		message.setFrom(new InternetAddress("supersonic97@naver.com", "LinkMall_Admin"));// 보내는 사람
	
		return message;
	}
	
	
	
	public String createKey() {
		StringBuilder key = new StringBuilder();
		Random random = new Random();
		
		for(int i = 0; i < 8; i++) {
			int index = random.nextInt(3);
			
			switch(index) {
			case 0:
				// a~z (ex. 1+97=98 => (char)98 = 'b')
				key.append((char) ((int)(random.nextInt(26)) + 97));
				break;
			case 1:
				// A ~ Z
				key.append((char) ((int)(random.nextInt(26)) + 65));
				break;
			case 2:
				// 0 ~ 9
				key.append((random.nextInt(10)));
				break;
			}
		}
		
		return key.toString();
	}
	
	public String sendSimpleMessage(String to)  {
		// 인증번호 생성
		 ePw = createKey();
		 
		 try {
			 // 메세지 생성
			 MimeMessage message = createMessage(to, "code");
			 
			 // 레디스에 인증번호 저장, 2분 
			 emailValidateRepository.setValue(to, ePw);
			 
			 // 메세지 전송
			 mailSender.send(message);
		 } catch (Exception e) {
			 throw new IllegalArgumentException();
		 }
		 
		 return ePw;
	}
	
	public String sendPassword(String to, String newPassword) {
		 ePw = createKey();
		 
		 try {
			 
		 MimeMessage message = createPassword(to, "pwd", newPassword);
			 mailSender.send(message);
		 } catch (Exception e) {
			 e.printStackTrace();
//			 throw new IllegalArgumentException();
		 }
		 
		 return ePw;
	}
	
	public boolean verifiedCode(String email, String code) {
		System.out.println("여기");
		String value = emailValidateRepository.getValues(email);
		System.out.println(value);
		System.out.println(code);
		if(value != null && value.equals(code)) {
			return true;
		}
		
		return false;
	}
	
	public boolean verifiedEmailByLoginId(String email, String loginId) {
		Member existMember = memberService.getMemberByLoginId(loginId);
		
		// 멤버가 존재하지 않을경우
		if(existMember == null) {
			return false;
		}
		
		// 멤버는 존재하지만 등록된 아이디와 이메일이 일치하지 않는경우 
		if(!existMember.getEmail().equals(email)) {
			return false;
		}
		
		// 모두 일치하는 경우 인증번호를 발송한다.
		sendSimpleMessage(existMember.getEmail());
		return true;
	}

}
