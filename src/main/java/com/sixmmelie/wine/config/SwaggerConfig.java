package com.sixmmelie.wine.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

/* 명세화된 페이지를 설정하는 클래스*/

@OpenAPIDefinition(
		info = @Info(title = "메뉴 조회 및 주문 서비스 API 명서세",
				description = "React부터 Spring Data Jpa까지 진행하는 서비스 API 명세서",
				version = "v1"))

@Configuration
public class SwaggerConfig {

	@Bean
	public GroupedOpenApi charOpneApi() {
		String [] paths = {"/api/v1/**", "/auth/**"};
		
		return GroupedOpenApi.builder()
								.group("주문 서비스 API v1")
								.pathsToMatch(paths)
								.build();
	}
}
