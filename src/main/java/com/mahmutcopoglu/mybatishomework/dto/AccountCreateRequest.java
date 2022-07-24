package com.mahmutcopoglu.mybatishomework.dto;

import com.mahmutcopoglu.mybatishomework.enums.AccountType;

import lombok.Data;

@Data
public class AccountCreateRequest {
	
	private String name;
	private String surname;
	private String email;
	private String tc;
	private AccountType type;
	

}
