package com.mahmutcopoglu.mybatishomework.entity;

import org.apache.ibatis.type.Alias;

import lombok.Data;


@Alias("Log")
@Data
public class Log {
	
	private int id;
	private long accountNumber;
	private String message;

}
