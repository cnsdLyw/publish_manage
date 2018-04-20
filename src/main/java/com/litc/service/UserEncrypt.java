/*
 * UserEncrypt.java  
 * Copyright (c) 2007,������������޹�˾����ý�忪����
 * All rights reserved.
 */
package com.litc.service;

import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

class UserEncrypt {
	private static final byte[] USER_PASSWORD_KEY = new byte[] { 64, 11, -110,
			11, 107, -2, -12, -83, 88, -118, 91, 84, 47, 84, 76, 98, 110, 84,
			-14, -48, 121, -14, 69, -94 };

	private final String algorithm = "DESede";
	private final SecretKey secretKey1 = new SecretKeySpec(USER_PASSWORD_KEY,
			algorithm);

	static {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
	}

	private static final UserEncrypt instance = new UserEncrypt();

	private UserEncrypt() {
	}

	static UserEncrypt getInstance() {
		return instance;
	}


	String encrypt(String plainString) {
		String result = null;
		try {
			Cipher c1 = Cipher.getInstance(algorithm);
			c1.init(Cipher.ENCRYPT_MODE, secretKey1);
			byte[] cipherByte = c1.doFinal(plainString.getBytes());
			result = byte2hex(cipherByte);
		} catch (Exception e) {
			// do nothing
		}
		return "3DES{" + result + "}====";
	}

	String decrypt(String encryptString) {
		if (!isDecryptText(encryptString))
			return encryptString;

		encryptString = encryptString.substring(5, encryptString.length() - 5);

		String result = null;
		try {
			byte[] encryptByte = hex2byte(encryptString);

			Cipher c1 = Cipher.getInstance(algorithm);
			c1.init(Cipher.DECRYPT_MODE, secretKey1);
			byte[] clearByte = c1.doFinal(encryptByte);

			result = new String(clearByte);
		} catch (Exception e) {
			// do nothing
		}
		return result;
	}

	boolean isDecryptText(String text) {
		if (text != null && text.startsWith("3DES{") && text.endsWith("}===="))
			return true;
		else
			return false;
	}

	private String byte2hex(byte[] b) 
	{
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
			if (n < b.length - 1)
				hs = hs + "";
		}
		return hs.toUpperCase();
	}

	private byte[] hex2byte(String hex) 
	{
		byte[] _byte = new byte[hex.length() / 2];
		for (int i = 0; i < (hex.length() / 2); i++) {
			_byte[i] = (byte) Integer.parseInt(hex.substring(i * 2, i * 2 + 2),
					16);
		}

		return _byte;
	}

	// public static void main(String[] args){
	// UserEncrypt ue = new UserEncrypt();
	// long start = System.currentTimeMillis();
	// for(int i=0;i<10000;i++){
	// String encrypt = ue.encrypt("aKkfsIeo3o3o2o2ojls;z.v,keo"+i);
	// System.out.println("encryptText="+encrypt);
	// String plainText= ue.decrypt(encrypt);
	// System.out.println("plainText="+plainText);
	// }
	// System.out.println("t="+(System.currentTimeMillis()-start));
	// //t=2484
	// System.out.println(ue.encrypt("sssssS"));
	// }
}
