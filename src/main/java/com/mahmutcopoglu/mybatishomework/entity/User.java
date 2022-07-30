package com.mahmutcopoglu.mybatishomework.entity;

import org.apache.ibatis.type.Alias;


import lombok.Data;

@Alias("User")
@Data
public class User {
	
	private int id;
	private String username;
	private String password;
	private String authorities;
	
}
