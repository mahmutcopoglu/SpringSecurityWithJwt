package com.mahmutcopoglu.mybatishomework.exchange.dto;

import java.util.List;

import org.springframework.stereotype.Component;

@lombok.Data
@Component
public class Result {
	
	private String base;
	private String lastupdate;
	private List<Data> data;
}
