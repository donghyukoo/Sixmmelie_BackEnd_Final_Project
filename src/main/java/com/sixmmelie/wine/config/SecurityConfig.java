package com.sixmmelie.wine.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.sixmmelie.wine.jwt.JwtAccessDeniedHandler;
import com.sixmmelie.wine.jwt.JwtAuthenticationEntryPoint;
import com.sixmmelie.wine.jwt.TokenProvider;

@EnableWebSecurity
public class SecurityConfig {
	/* 의존성 주입을 받기위한 구문 작성할 곳 Tokenprovider 등등 */
	private final TokenProvider tokenProvider;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
   
    @Autowired
    public SecurityConfig(TokenProvider tokenProvider
                  , JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint
                  , JwtAccessDeniedHandler jwtAccessDeniedHandler) {
    	this.tokenProvider = tokenProvider;
    	this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    	this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }
   
   	/* 1. 암호화 처리를 위한 PasswordEncoder를 Bean으로 설정(빈을 등록할때는 메소드 이름에 오타가 없도록해야한다.) */
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
		
	}
	
	/* 2. 시큐리티 설정을 무시 할 정적 리소스 등록 (보안처리를 안해도될것들 기본적인 css,html등과 우리 와인 이미지들)*/
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().antMatchers("/css/**", "/js/**", "images/**"
											, "/lib/**", "infoimgs/**", "wineimgs/**");	// 여기에 우리가 DB에 저장 할 이미지도 추가해야함
	}
	
	/* ======== 여기서부터 session방식과 달라지는 부분이 존재함 =======*/
	/* 
	 	Authentication = 인증 인증은 클라이언트가 로그인시에 요청한  id,pw,권한등을 DB에서 조회하여 우리 회원이 맞는지 인증을 하는 단계이다. 
	  	Authorization = 인가 인증되서 로그인된 회원이 어떠한 권한을 갖고있으며, 그 권한으로 어느 경로까지 접근이 가능한지 확인하는 단계이다. 
	 */
	/* 3. HTTP요청에 대한 권한별 설정 (세션인증방식을 사용하지않고 토큰인증방식으로 인증하겠다는 부분이 들어감 */

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http.csrf().disable()	// 토큰 위조 공격 방지 관련해서 처리하지않는 구문 이유가 뭔지 기억이 안나네 
			.exceptionHandling()
			
			/* 기본 시큐리티 설정에 + JWT 토큰과 관련된 유효성과 권한 체크를 설정하는 구문 */
			/* 아직 JWT 클래스들을 만들지 않아 오류가나서 주석처리 해둠 */
			.authenticationEntryPoint(jwtAuthenticationEntryPoint)		// 유효한 자격  증명이 없을 시 (401 error)예외처리 인증,인가가 되지 않은 사용자
			.accessDeniedHandler(jwtAccessDeniedHandler)				// 인증,인가는 됐지만  low레벨에서 high레벨에 접근할 시 (403 error)예외처리 
			.and()
			.authorizeRequests()
				.antMatchers("/").authenticated()
				.antMatchers(HttpMethod.OPTIONS,"/**").permitAll()		// cors를 위해 preflight를 사용하기위한 options 요청허용이다.
																		// preflight request란?
																		// 요청 할 url이 외부 도메인일 경우 웹 브라우저에서 자체 실행되며 options 메소드로 사전 요청을 보내게 된다.
																		// 사전에 요청이 안전한지 확인하기 위함(유효한지,안전한지 서버에 미리 파악할 수 있도록 보내는 수단이다.)
																		// 이 부분에 권한별 접근 설정 할 수 있다.(전 프로젝트 시큐리티 설정과 유사함 hasRole,hasAnyRole)
			.antMatchers("/api/v1/**").permitAll()			// 권한이 없어도 볼 수 있어야 하는 화면은 permitall()을 작성해줘야한다.(와인리스트 같은 경우)
			//.antMatchers("/api/v1/**/**").permitAll()			// 권한이 없어도 볼 수 있어야 하는 화면은 permitall()을 작성해줘야한다.(와인리스트 같은 경우)
//			.antMatchers("/auth/**").permitAll()
//			.antMatchers("/api/v1/information/**").permitAll()			// 나중에 틀이 갖춰지면 만들 내용
//			.antMatchers("/api/v1/informations/**").permitAll()
//			.antMatchers("/api/v1/products4/**").permitAll()
			.anyRequest().permitAll()									// 처음 배포할 때 로그인 및 권한이 없어도 요청이든 허용 되도록 열어둠
			.and()
		
			/* 인증방식 설정하는 구문 session방식을 쓰지 않고 Token방식으로 인증하겠다는 구문이 작성되는 부분 */
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()	
				.cors()
			.and()
			
			/* jwt 인증 방식을 쓰겠다는 구문이 작성되는 부분 */
			.apply(new JwtSecurityConfig(tokenProvider));
		
		return http.build();
	}
	
	/* 4. CORS 설정하는부분 CORS를 사용해서 SOP 정책에 막히지 않고 해결하게 해줄 수 있는 구문이 들어가는 부분 */
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
																						
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));		// 오픈api는 허용범위가 전체공개로 되어있지만 우리는 우리 프로젝트 리액트 서버만 받아준다고 작성을 했다.
																						// 포트 번호는 우리 리액트껄로 바꿔야함 아직 안바꿈! 
		configuration.setAllowedMethods(Arrays.asList("GET", "PUT", "POST", "DELETE"));
		configuration.setAllowedHeaders(Arrays.asList("Access-Control-Allow-Origin", "Content-type"
													, "Access-Control-Allow-Headers", "Authorization"
													, "X-Requested-With"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
	
}


