package com.mahmutcopoglu.mybatishomework.dto;

import lombok.Data;

@Data
public class MoneyTransferRequest {
	
	private int transferredAccountId;
	private double amount;
}
