package com.mahmutcopoglu.mybatishomework.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mahmutcopoglu.mybatishomework.entity.Account;
import com.mahmutcopoglu.mybatishomework.enums.AccountType;


@Component
public class MyBatisAccountRepository{

	@Autowired
	private AccountRepository accountRepository;

	
	public Account saveAccount(
			String name,
			String surname,
			String email,
			String tc,
			AccountType type) {
		 
		Account account = new Account();
		long number = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
		account.setAccountNumber(number);
		account.setBalance(0);
		account.setName(name);
		account.setEmail(email);
		account.setSurname(surname);
		account.setTc(tc);
		account.setType(type);
		account.setDeleted(false);
		account.setLastModified(System.currentTimeMillis());
		
		this.accountRepository.saveAccount(account);
		return account;
		
	}
	
	public Account findById(int id) {
		return this.accountRepository.findById(id);
	}
	
	
	public Account updateBalance(int id, double balance) {
		if(this.accountRepository.updateAccountAmount(id,balance)>0) {
			return this.accountRepository.findById(id);
		}
		return null;
		
	}
	
	public void transfer(double amount, int ownerId) {
		this.accountRepository.transfer(amount, ownerId);
	}
	
	public int deleteAccount(int id) {
		return this.accountRepository.deleteAccount(id);
	}
	
	
	

}
