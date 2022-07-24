package com.mahmutcopoglu.mybatishomework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mahmutcopoglu.mybatishomework.entity.Account;
import com.mahmutcopoglu.mybatishomework.enums.AccountType;
import com.mahmutcopoglu.mybatishomework.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository myBatisAccountRepository;
	
	@Autowired
	private ExchangeService exchangeService;
	
	
	@Transactional
	@Override
	public Account save(String name, String surname, String email, String tc, AccountType type) {
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
		this.myBatisAccountRepository.saveAccount(account);
		return account;
		
	}

	@Transactional
	@Override
	public Account findById(int id) {
		Account account = this.myBatisAccountRepository.findById(id);
		return account;
	}

	@Transactional
	@Override
	public Account updateBalance(int id, double balance) {
		
		Account account = this.myBatisAccountRepository.findById(id);
		if(this.myBatisAccountRepository.updateAccountAmount(id, account.getBalance() + balance)>0) {
			return this.myBatisAccountRepository.findById(id);
		}
		return null;
	}

	@Transactional
	@Override
	public boolean transfer(double amount, int ownerAccountId, int transferAccountId) {
		Account ownerAccount = this.myBatisAccountRepository.findById(ownerAccountId);
		Account transferAccount = this.myBatisAccountRepository.findById(transferAccountId);
		double transferAmount = amount;
		
		if(ownerAccount.getBalance() < amount && amount<=0) {
			return false;
		}
		
		if(!ownerAccount.getType().equals(transferAccount.getType())) {
			transferAmount = this.exchangeService.exchange(amount, ownerAccount.getType(), transferAccount.getType());
			System.out.println(transferAmount);
		}
		
		this.myBatisAccountRepository.transfer(ownerAccount.getBalance() - amount, ownerAccountId);
		this.myBatisAccountRepository.transfer(transferAccount.getBalance() + transferAmount, transferAccountId);
		return true;
		
	}
	
	public boolean delete(int id) {
		return this.myBatisAccountRepository.deleteAccount(id)>0;
		
	}

}
