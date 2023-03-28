package com.sixmmelie.wine.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/* 
 	OncePerRequestFilter를 상속 받아서 메소드를 오버라이딩하여 정의하고
 	OncePerRequestFilter: 사용자의 요청에 한번만 동작하는 필터로써
  	클라이언트측에서 로그인을 할때 아이디 비밀번호를 입력하면 동작하며  
  	UsernamePasswordAuthenticationFilter 바로 앞에서 동작한다.
  	이 필터를 거처서 UsernamePasswordAuthenticationFilter를 진행하고
  	내부적으로 UserDetailService등을 통해 DB에 일치하는 회원을 조회하며 일치하면 통과한다. 
 */
public class JwtFilter extends OncePerRequestFilter {
	
	   private static final Logger log = LoggerFactory.getLogger(JwtFilter.class);

	   public static final String AUTHORIZATION_HEADER = "Authorization";   // 사용자가 request header에 Authorization 속성으로 token을 던짐
	   public static final String BEARER_PREFIX = "Bearer ";            // 사용자가 던지는 토큰 값만 파싱하기 위한 접두사 저장용 변수(접두사는 Bearer라는 표준으로 정의 됨)
	   
	   private final TokenProvider tokenProvider;
	   
	   public JwtFilter(TokenProvider tokenProvider) {
		   this.tokenProvider = tokenProvider;
	   }

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String jwt = resolveToken(request);		// 로그인 요청에서 토큰값을 추출한다. 요청한 사람이 토큰이 있는지 없는지 판별한다.

	/* 추출한 토큰의 유효성 검사 후 인증을 위해 Authentication 객체를 SecuricyContextHolder에 담는다.( 이 작업이 인증 작업) */
	if(StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {	// 베어러 다음에 넘어온 문자열이 맞는지 확인하기 위해 서버가 가진 비밀키를 가지고 토큰이 맞는지 아닌지 대조. 유효성 검사를 한다. 
	
		/* 벨리데이션 절차를 통과하면 실행되는구문 (인증이된다면) */
		Authentication authentication = tokenProvider.getAuthentication(jwt);	// 인증이 되면 시큐리티컨텍스트홀더에 담긴다.
		SecurityContextHolder.getContext().setAuthentication(authentication);	// 어센티케이션:인증 어서라이제이션:인가
	 }
	filterChain.doFilter(request, response);		// 다음 filterchain진행
	}
	
	/* Request Header에서 토큰 정보 꺼내기(위에 정의한 static final 변수 두개를 사용한다.) */
	private String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader(AUTHORIZATION_HEADER);	// "Autuorization" 인가
		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(bearerToken)) {
			return bearerToken.substring(7);	// 표준으로 정의되어있기 때문에 알고리즘도 변하지 않고 인덱스 체계로 토큰값을
												// Bearer(한칸 뜀) <- 공백까지 기준으로 7번째 인덱스부터 파싱한다. 설정이며 변하지 않는다.
		}
		return null;
	}
	
}
