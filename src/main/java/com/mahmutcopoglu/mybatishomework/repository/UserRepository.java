package com.mahmutcopoglu.mybatishomework.repository;

import org.apache.ibatis.annotations.Mapper;

import com.mahmutcopoglu.mybatishomework.entity.User;

@Mapper
public interface UserRepository {

	public User findByUsername(String username);
}
