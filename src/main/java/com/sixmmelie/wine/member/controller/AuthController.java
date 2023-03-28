package com.sixmmelie.wine.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sixmmelie.wine.common.ResponseDTO;
import com.sixmmelie.wine.member.dto.MemberDTO;
import com.sixmmelie.wine.member.service.AuthService;

/*
 * @RestController, @ResponseBody, ResponseEntity,CORS ( 핵심 키워드 )
 * 
 * 	@ResponseBody란 리스판스 바디에 담길 내용이 데이터라는걸 명시적으로 표현하는 어노테이션
 * 
 * 1. @RestController란(@Controller + @ResponseBody)?
 *    @ResponseBody를 포함한 컨트롤러로써 응답 body에 담긴 데이터는 Spring boot에서 기본적으로 제공하는
 *    MappingJackson2HttpMessageConverter가 내부적으로 ObjectMapper를 활용하여 UTF-8인코딩 타입
 *    및 application/json MIME 타입의 json문자열로 반환한다. 		
 *    
 *    즉, @RestController가 달리면 요청을 내보낼때 json문자열로 내보낸다.
 * 
 * 2. ResponseEntity반환. ResponseEntity란?
 * 	  응답으로 변환 될 정보를 모두 담은 요소들을 객체로 만들어서 반환해준다.(body와 header와 status)
 * 	  (주로 status와 body만 신경쓰면 된다.)
 * 	  ResponseEntity를 사용할 때, 생성자 대신 Builder를 사용하는걸 권장한다.
 * 		(빌더란?메소드체이닝 즉 메소드를 이어붙이는 작업)
 * 		ResponseDTO(HttpStatus.OK를 사용하는이유
 * 	  (숫자로 된 상태 코드를 실수로 잘못 넣지 않도록 메소드들이 제공 된다.)	
 * 	 enum클래스로되어있다.
 */

@RestController
@RequestMapping("/auth")	// 여기 경로는 고정은 아닐텐데 우리가 설정하는대로 바꿔줘야 할듯?
public class AuthController {

	private final AuthService authService; // memberService패키지에 AuthService클래스를 만들어서 필드주입 받을려고 작성한 구문

	@Autowired
	public AuthController(AuthService authService) {
		this.authService = authService;
	}
	
	/* @RequestBody를 통해서 RequestBody로 넘어온 Json 문자열을 파싱해서 MemberDTO 속성으로 매핑해 객체로 받아낸다.(회원 아이디, 비밀번호) */
	
//	@Operation(summary = "회원 가입 요청", description = "회원 가입이 진행됩니다.", tags = {"AuthController"}) // 여기는 스웨거 때문에 어노테이션 달은듯
	@PostMapping("/login")
	public ResponseEntity<ResponseDTO> login(@RequestBody MemberDTO memberDTO){
		return ResponseEntity
				.ok()
				.body(new ResponseDTO(HttpStatus.OK,"로그인 성공", authService.login(memberDTO)));	// @service어노테이션으로 의존성 주입을 받을거다.
		
		/*
					( ★★★★★ 가장 중요한 내용 ★★★★★ ) 이 부분을 이해하면 프로젝트를 할 수 있다.
				ResponseEntity의 body메소드를 통해 Response객체의 body에 담기는 ResponseDTO는 JSOM문자열이 되고
				화면단이 React인 곳으로 가면 결국 Store에 해당 리듀서가 관리하는 state값이 된다.
				여기를 이해하면 리액트와 스프링부트가 api통신을 통해 어떻게 동작하는지 이해할 수 있을거 같다.
		 */
	}
	
//	@Operation(summary = "회원 가입 요청", description = "회원 가입이 진행됩니다.", tags = {"AuthController"})	// 스웨거 어노테이션 
	@PostMapping("/signup")
	public ResponseEntity<ResponseDTO> signup(@RequestBody MemberDTO memberDTO){	// 회원 가입 정보를 MemberDTO 속성으로 매핑해 받아낸다
		return ResponseEntity
				.ok()
				.body(new ResponseDTO(HttpStatus.CREATED,"회원가입 성공", authService.signup(memberDTO)));
	}
}
