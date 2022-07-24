package com.mahmutcopoglu.mybatishomework.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mahmutcopoglu.mybatishomework.entity.Log;

@Mapper
public interface LogRepository {

	void saveLog(Log log);
	
	public List<Log> findByAccountNumber(long accountNumber);
}
