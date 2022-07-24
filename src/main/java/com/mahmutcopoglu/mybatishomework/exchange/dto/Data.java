package com.mahmutcopoglu.mybatishomework.exchange.dto;

import org.springframework.stereotype.Component;

@lombok.Data
@Component
public class Data {
	
	private String code;
	private String name;
	private String rate;
	private String calculatedstr;
	private double calculated;
}
