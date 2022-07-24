package com.mahmutcopoglu.mybatishomework.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.mahmutcopoglu.mybatishomework.enums.AccountType;
import com.mahmutcopoglu.mybatishomework.exchange.dto.CollectApiResponse;

@Component
public class ExchangeServiceImpl implements ExchangeService {

	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${exchange.content.type}")
	private String contentType;
	@Value("${exchange.authorization}")
	private String authorization;
	
	@Override
	public double exchange(double amount, AccountType base, AccountType to) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("content-type", contentType);
		headers.set("authorization", authorization);
		HttpEntity<String> model = new HttpEntity<>(headers);
		ResponseEntity<CollectApiResponse> collectApiResponse = restTemplate.exchange("https://api.collectapi.com/economy/exchange?int=" + amount + "&to=" + to + "&base=" + base,
				HttpMethod.GET, model,CollectApiResponse.class);
		return collectApiResponse.getBody().getResult().getData().stream().findFirst().get().getCalculated();
		
		

	}
	
	

	

}
