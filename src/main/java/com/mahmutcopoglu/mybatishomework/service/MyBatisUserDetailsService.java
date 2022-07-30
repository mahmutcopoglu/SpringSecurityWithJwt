package com.mahmutcopoglu.mybatishomework.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.mahmutcopoglu.mybatishomework.dto.UserDatabase;
import com.mahmutcopoglu.mybatishomework.entity.User;
import com.mahmutcopoglu.mybatishomework.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MyBatisUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if(user==null) {
			log.error("User not found in the database");
			throw new UsernameNotFoundException("User not found in the database");
		}else {
			log.info("User found in the database: {}",username);
		}
		
		String[] allAuthorities = user.getAuthorities().split(",");
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		for (String auth : allAuthorities) {
			authorities.add(new SimpleGrantedAuthority(auth));
		}
		
		return new UserDatabase(user.getId(),user.getUsername(), user.getPassword(), authorities);
	}

}
