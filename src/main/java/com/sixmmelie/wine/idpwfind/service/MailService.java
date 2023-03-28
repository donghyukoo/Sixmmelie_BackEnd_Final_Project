package com.sixmmelie.wine.idpwfind.service;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
	
	private static final Logger log = LoggerFactory.getLogger(MailService.class);

	@Autowired
	private JavaMailSender javaMailSender;
	
	public void sendMail(String mail, String title, String content) {
		// 수신 대상을 담을 ArrayList 생성
		ArrayList<String> toUserList = new ArrayList<>();
		log.info("mail>>"+mail+"|||subject>>"+title+"|||contnet>>"+content);
		// 수신 대상 추가
		toUserList.add(mail);
		
		// 수신 대상 개수 
		int toUserSize = toUserList.size();
		
		//SimpleMailMessage(단순 텍스트 구성 메일 메시지 생성할 때 이용)
		SimpleMailMessage simpleMessage = new SimpleMailMessage();
		
		//수신자 설정
		simpleMessage.setTo((String[]) toUserList.toArray (new String[toUserSize]));
		
		//메일 제목
		simpleMessage.setSubject(title);
		
		//메일 내용
		simpleMessage.setText(content);
		
		//메일 발송
		javaMailSender.send(simpleMessage);
	}
	
}
