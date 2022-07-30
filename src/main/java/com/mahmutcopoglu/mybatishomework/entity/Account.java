package com.mahmutcopoglu.mybatishomework.entity;

import org.apache.ibatis.type.Alias;

import com.mahmutcopoglu.mybatishomework.enums.AccountType;

import lombok.Data;

@Alias("Account")
@Data
public class Account {
	
	private int id;
	private long accountNumber;
	private String name;
	private String surname;
	private String email;
	private String tc;
	private AccountType type;
	private double balance;
	private long lastModified;
	private boolean deleted;
	private int userId;
	

}
