package com.mahmutcopoglu.mybatishomework.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.mahmutcopoglu.mybatishomework.repository.MyBatisLogRepository;


@Component
public class Consumer {
	
	@Autowired
	private MyBatisLogRepository myBatisLogRepository;

	@KafkaListener(topics = { "log" }, groupId = "log_group")
	public void listenTransfer(@Payload String message) {
		this.myBatisLogRepository.saveLog(message);
	}
}
