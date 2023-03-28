package com.sixmmelie.wine.config;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.sixmmelie.wine.jwt.JwtFilter;
import com.sixmmelie.wine.jwt.TokenProvider;

/* Jwr관련 필터를 UsernamePasswordAuthenticationFilter 앞 쪽에 추가 */
/* 어노테이션을 작성하지 않아도 된다.(수동적으로 호출하기 때문이다.)*/
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>{
	private final TokenProvider tokenProvider;
	
	@Autowired
	public JwtSecurityConfig(TokenProvider tokenProvider) {
		this.tokenProvider = tokenProvider;
	}
	
	@Override
	public void configure(HttpSecurity http) {
		JwtFilter customFilter = new JwtFilter(tokenProvider);							// JwtFilter를 jwt 패키지에 추가한다.
		http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);	// JwtFilter를 Filterchain상에 추가한다.
	}
}
