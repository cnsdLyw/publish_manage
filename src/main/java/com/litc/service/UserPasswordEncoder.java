package com.litc.service;

import org.springframework.security.crypto.password.PasswordEncoder;

public class UserPasswordEncoder implements PasswordEncoder  {

	/**
	 * 加密
	 */
	@Override
	public String encode(CharSequence rawPassword) {
		return UserEncrypt.getInstance().encrypt(rawPassword.toString());
	}

	/**
	 * 登录时验证密码
	 * rawPassword 明文
	 * password    数据库保存的已加密的
	 */
	@Override
	public boolean matches(CharSequence rawPassword, String password) {
		System.out.println("matches  "+rawPassword+"   "+password);
		return UserEncrypt.getInstance().encrypt(rawPassword.toString()).equals(password);
	}

}
