package com.mahmutcopoglu.mybatishomework.service;

import com.mahmutcopoglu.mybatishomework.enums.AccountType;

public interface ExchangeService {

	public double exchange(double amount, AccountType base, AccountType to);
}
