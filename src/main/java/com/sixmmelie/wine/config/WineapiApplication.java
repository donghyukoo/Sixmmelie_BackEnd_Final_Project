package com.sixmmelie.wine.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.sixmmelie.wine")
public class WineapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(WineapiApplication.class, args);
	}
	
}
