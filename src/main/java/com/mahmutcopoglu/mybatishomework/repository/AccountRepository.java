package com.mahmutcopoglu.mybatishomework.repository;

import org.apache.ibatis.annotations.Mapper;

import com.mahmutcopoglu.mybatishomework.entity.Account;

@Mapper
public interface AccountRepository {

	void saveAccount(Account account);
	
	public Account findById(int id);
	
	int updateAccountAmount(int id,double balance);
	
	void transfer(double balance, int accountId);
	
	int deleteAccount(int accountId);

}
