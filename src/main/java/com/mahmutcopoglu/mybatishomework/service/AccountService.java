package com.mahmutcopoglu.mybatishomework.service;

import com.mahmutcopoglu.mybatishomework.entity.Account;
import com.mahmutcopoglu.mybatishomework.enums.AccountType;

public interface AccountService {
	
	public Account save(String name,
			String surname,
			String email,
			String tc,
			AccountType type
	);
	
	public Account findById(int id);
	
	Account updateBalance(int id, double balance);
	
	boolean transfer(double amount, int ownerAccountId, int transferAccountId);
	
	boolean delete(int id);
}
