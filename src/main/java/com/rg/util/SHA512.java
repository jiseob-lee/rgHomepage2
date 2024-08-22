package com.rg.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA512 {
	
	public static String encrypt(String raw) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-512");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		md.update(raw.getBytes());
		String hex = String.format("%0128x", new BigInteger(1, md.digest()));
		return hex;
	}
	
	public static void main(String[] args) {
		String enc = SHA512.encrypt("111");
		System.out.println(enc);
	}
}
