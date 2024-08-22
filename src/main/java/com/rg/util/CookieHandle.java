package com.rg.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CookieHandle {

	public static void setCookie(HttpServletResponse res, String cookieName, String value) {
	    Cookie cookie = new Cookie(cookieName, value); // 쿠키 이름 지정하여 생성( key, value 개념)
	    //cookie.setMaxAge(60*60*24*100); //쿠키 유효 기간: 하루로 설정(60초 * 60분 * 24시간)
	    cookie.setPath("/"); //모든 경로에서 접근 가능하도록 설정
	    //cookie.setDomain(".jisblee.me");
	    res.addCookie(cookie); //response에 Cookie 추가
	}
	
	public static String getCookie(HttpServletRequest req, String cookieName) {
		try {
	    Cookie[] cookies = req.getCookies(); // 모든 쿠키 가져오기
	    if (cookies != null) {
	        for (Cookie c : cookies) {
	            String name = c.getName(); // 쿠키 이름 가져오기
	            String value = c.getValue(); // 쿠키 값 가져오기
	            if (name.equals(cookieName)) {
	                return value;
	            }
	        }
	    }
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return null;
	}
	
}
