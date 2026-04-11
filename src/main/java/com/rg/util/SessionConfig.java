package com.rg.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;

//@WebListener
public class SessionConfig {//implements HttpSessionListener {

	private static final Map<String, HttpSession> sessions = new ConcurrentHashMap<>();
	
	//중복로그인 지우기
	public synchronized static String getSessionidCheck(String type, String compareId){
		String result = "";
		System.out.println("#### sessionSize 2 : " + sessions.keySet().size());
		for( String key : sessions.keySet() ){
			HttpSession hs = sessions.get(key);
			
			if(hs != null &&  hs.getAttribute(type) != null && hs.getAttribute(type).toString().equals(compareId) ){
				result =  key.toString();
			}
		}
		removeSessionForDoubleLogin(result);
		return result;
	}
	
	private static void removeSessionForDoubleLogin(String userId){    	
		System.out.println("remove userId : " + userId);
		if(userId != null && userId.length() > 0){
			sessions.get(userId).invalidate();
			sessions.remove(userId);    		
		}
	}
	
	//@Override
	public void sessionCreated(HttpSessionEvent se) {
		System.out.println("#### sessionCreated : " + se);
	    sessions.put(se.getSession().getId(), se.getSession());
	}
	
	//@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		System.out.println("#### sessionDestroyed");
		if(sessions.get(se.getSession().getId()) != null){
			sessions.get(se.getSession().getId()).invalidate();
			sessions.remove(se.getSession().getId());	
		}
	}
	
	public static List<String> getSessions() {
		List<String> list = new ArrayList<>();
		System.out.println("#### sessionSize : " + sessions.keySet().size());
		for( String key : sessions.keySet() ){
			list.add(key);
		}
		return list;
	}
}