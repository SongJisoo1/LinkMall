package com.example.demo.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {
	
	@Bean
	public JavaMailSender javaMailSender() {
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		
		javaMailSender.setHost("smtp.naver.com"); // smtp 서버 주소
		javaMailSender.setUsername("supersonic97@naver.com"); // smtp로 등록한 아이디
		javaMailSender.setPassword("theowkd1!");
		
		javaMailSender.setPort(465);
		
		javaMailSender.setJavaMailProperties(getMailProperties());
		
		return javaMailSender;
	}
	
	
	// 프로퍼티 생성
	private Properties getMailProperties() {
		Properties properties = new Properties();
		
		properties.setProperty("mail.transport.protocol", "smtp"); // 프로토콜
		properties.setProperty("mail.smtp.auth", "true"); // smtp 인증
		properties.setProperty("mail.smtp.starttls.enable", "true"); // smtp strattles 사용
		properties.setProperty("mail.debug", "true");
		properties.setProperty("mail.smtp.ssl.trust", "smtp.naver.com"); // ssl 인증 서버는 smtp.naver.com
		properties.setProperty("mail.smtp.ssl.enable", "true"); // ssl 사용
		
		return properties;
 	}

}