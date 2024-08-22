package com.rg.test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Encoding {
	public static void main(String[] args) {
		try {
			System.out.println(URLEncoder.encode("가나다.txt", "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
