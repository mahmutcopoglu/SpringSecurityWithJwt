package com.mahmutcopoglu.mybatishomework;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
public class MybatishomeworkApplication {

	public static void main(String[] args) {
		SpringApplication.run(MybatishomeworkApplication.class, args);
	}
	
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	

}
