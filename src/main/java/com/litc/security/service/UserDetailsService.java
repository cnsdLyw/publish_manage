package com.litc.security.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserDetailsService {

	 UserDetails loadUserByUsername(String username,HttpServletRequest request) throws UsernameNotFoundException;
	
}
