package com.mahmutcopoglu.mybatishomework.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mahmutcopoglu.mybatishomework.entity.Log;


@Component
public class MyBatisLogRepository {

	@Autowired
	private LogRepository logRepository;
	
	
	
	public void saveLog(String message) {
		
		String[] parsedText = message.split(" ");
		Log log = new Log();
		if(parsedText[1].contains("deposit")) {
			log.setAccountNumber(Long.parseLong(parsedText[0]));
			log.setMessage(parsedText[0] + " nolu hesaba " + message.split("amount:")[1] + " yatırılmıştır." );
		
		}else {
			log.setAccountNumber(Long.parseLong(parsedText[0]));
			System.out.println(message);
			log.setMessage(parsedText[0]+" hesaptan "+ message.split("transferred_account:")[1] +" hesaba "+((message.split(",")[0]).split("amount:")[1]) + " yatırılmıştır.");
		}
		
		this.logRepository.saveLog(log);
		
		
	}
	
	public List<Log> findByAccountNumber(long accountNumber) {
		return this.logRepository.findByAccountNumber(accountNumber);
	}
}
