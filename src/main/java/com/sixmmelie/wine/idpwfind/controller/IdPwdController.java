package com.sixmmelie.wine.idpwfind.controller;

import java.util.Random;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sixmmelie.wine.common.ResponseDTO;
import com.sixmmelie.wine.idpwfind.dto.MemberFindDTO;
import com.sixmmelie.wine.idpwfind.repository.MemberFindRepository;
import com.sixmmelie.wine.idpwfind.service.IdPwdService;
import com.sixmmelie.wine.idpwfind.service.MailService;
import com.sixmmelie.wine.info.dto.InformationDTO;
import com.sixmmelie.wine.member.dto.MemberDTO;
import com.sixmmelie.wine.member.repository.MemberRepository;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/api/v1")
public class IdPwdController {

	
	private static final Logger log = LoggerFactory.getLogger(IdPwdController.class);

	private IdPwdService idpwdService;
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	private MailService mailService;
		
	@Autowired
	public IdPwdController(IdPwdService idpwdService, BCryptPasswordEncoder bcryptPasswordEncoder, MailService mailService) {
		this.idpwdService = idpwdService;
		this.bcryptPasswordEncoder = bcryptPasswordEncoder;
		this.mailService = mailService;
	}
	
	@Operation(summary = "아이디 찾기", description = "입력한정보의 아이디", tags = { "IdPwdController" })
	@PostMapping(value="/idfind", produces="application/json; charset=UTF-8")
	@ResponseBody
	public JSONObject findId(@RequestBody String data) {
		// JSONParser로 JSONObject로 변환
	    JSONParser parser = new JSONParser();
	    JSONObject jObj = new JSONObject();
	    JSONObject rObj = new JSONObject();
	    try {
	    	jObj = (JSONObject) parser.parse(data);
			
			System.out.println(">>"+jObj.get("memberName"));
			System.out.println(">>"+jObj.get("memberEmail"));
			MemberFindDTO result = idpwdService.findByMemberNameAndMemberEmail((String)jObj.get("memberName"),(String)jObj.get("memberEmail"));
			log.info("result: " + result);
			if(result != null) {
				log.info("이메일 발송: " + result);
				String mailAddress = result.getMemberEmail();
				String title = "아이디 찾기";
				String content = "당신의 아이디는 "+result.getMemberId()+" 입니다.";
				
				log.info("mailAddress: " + mailAddress);
				log.info("title: " + title);
				log.info("content: " + content);
				mailService.sendMail(mailAddress, title, content);
				rObj.put("resultCode", 200);
				rObj.put("resultMessage", "찾으신 아이디는 가입하신 이메일로 전송하였습니다. 감사합니다.");
			}else {
				log.info("이메일 발송 실패: " + result);
				rObj.put("resultCode", 100);
				rObj.put("resultMessage", "검색된 아이디가 없습니다.");
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return rObj;
	}
	
	@Operation(summary = "비밀번호 찾기", description = "비밀번호 찾기", tags = { "IdPwdController" })
	@PostMapping(value="/pwfind", produces="application/json; charset=UTF-8")
	@ResponseBody
	public JSONObject findPw(@RequestBody String data) {
		JSONParser parser = new JSONParser();
        JSONObject jObj = new JSONObject();
        JSONObject rObj = new JSONObject();
        try {
        	jObj = (JSONObject) parser.parse(data);
			
			System.out.println(">>"+jObj.get("memberId"));
			System.out.println(">>"+jObj.get("memberEmail"));
			
			//memberId와 memberEmail로 member data 검색
			MemberFindDTO result = idpwdService.findByMemberIdAndMemberEmail((String)jObj.get("memberId"),(String)jObj.get("memberEmail"));
			
			//Random함수로 random password 생성
			Random ran = new Random();
			StringBuffer newPw = new StringBuffer();
			if(result.getMemberPw() != null && !result.getMemberPw().isEmpty()) {
				for(int i=0; i < 6; i++) {
					int index = ran.nextInt(3);
					switch(index) {
							case 0 : newPw.append((char)(ran.nextInt(26) + 97));
									break;
							case 1: newPw.append((char)(ran.nextInt(26) + 65));
									break;
							case 2: newPw.append(ran.nextInt(10));
									break;
					}
				}
			}
			
			//newPw는 자료형이 String이 아닌 StringBuffer이므로 toString을 사용하여 String 형태로 형변환 시켜줌 
			String password = newPw.toString();		//암호화 전 password
			
			//String형태로 변경된 패스워드를 bcrypt로 인코딩하여 암호화된 값을 받아옴
			String encdoePassword = bcryptPasswordEncoder.encode(password);	//암호화 후 password
			
			//업데이트에 사용할 신규 MemberDTO 생성
			MemberFindDTO mdto = new MemberFindDTO();
			mdto.setMemberNo(result.getMemberNo());
			mdto.setMemberPw(encdoePassword);
			//result.setMemberPw(encdoePassword);
			idpwdService.updatePw(mdto);
			
			/**
			 * 이메일 발송
			 * 새로 생성한 비밀번호를 전송함
			 */
			if(result != null) {
				String mailAddress = result.getMemberEmail();
				String title = "비밀번호 찾기";
				String content = "당신의 비밀번호는 "+password+" 입니다.";
				
				//sendMail에 이메일, 제목, 내용을 전달한다
				//전달하는 값들은 String으로 보낸다
				mailService.sendMail(mailAddress, title, content);
				rObj.put("resultCode", 200);
				rObj.put("resultMessage", "임시비번을 이메일로 전송하였습니다. 감사합니다.");
			}else {
				log.info("이메일 발송 실패: " + result);
				rObj.put("resultCode", 100);
				rObj.put("resultMessage", "검색된 아이디가 없습니다.");
			}
        } catch (ParseException e) {
			e.printStackTrace();
		}
		return rObj;
	}
	
}
