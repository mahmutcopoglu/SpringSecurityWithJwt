package com.mahmutcopoglu.mybatishomework.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.mahmutcopoglu.mybatishomework.entity.Log;
import com.mahmutcopoglu.mybatishomework.repository.LogRepository;


@Component
public class Consumer {
	
	@Autowired
	private LogRepository myBatisLogRepository;

	@KafkaListener(topics = { "log" }, groupId = "log_group")
	public void listenTransfer(@Payload String message) {
		
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
		
		this.myBatisLogRepository.saveLog(log);
	}
}
