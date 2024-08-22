package com.rg.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class HostName {
	
	public String getHostName(InetAddress inaHost) throws UnknownHostException {
		try {
			Class clazz = Class.forName("java.net.InetAddress");
			Constructor[] constructors = clazz.getDeclaredConstructors();
			constructors[0].setAccessible(true);
			InetAddress ina = (InetAddress) constructors[0].newInstance();

			Field[] fields = ina.getClass().getDeclaredFields();
			for (Field field : fields) {
				if (field.getName().equals("nameService")) {
					field.setAccessible(true);
					Method[] methods = field.get(null).getClass().getDeclaredMethods();
					for (Method method : methods) {
						if (method.getName().equals("getHostByAddr")) {
							method.setAccessible(true);
							return (String) method.invoke(field.get(null), inaHost.getAddress());
						}
					}
				}
			}
		} catch (ClassNotFoundException cnfe) {
		} catch (IllegalAccessException iae) {
		} catch (InstantiationException ie) {
		} catch (InvocationTargetException ite) {
			throw (UnknownHostException) ite.getCause();
		}
		return null;
	}
	
	public static void main(String[] args) {
		InetAddress address = null;
		try {
			address = InetAddress.getByName("172.30.1.38");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		HostName hostName = new HostName();
		String hostname = null;
		try {
			hostname = hostName.getHostName(InetAddress.getLocalHost());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		System.out.println(hostname);
		

		// InetAddress ip 정보와 host 정보를 가지는 객체
		// InetAddress 객체는 생성자가 아닌 Static 메소드를 이용해서 생성한다.

		try {
			// 1. host 이름에 해당하는 ip정보를 가진 inetAddress 객체 얻기
			InetAddress ipinfo1 = InetAddress.getByName("www.google.com");
			String ip = ipinfo1.getHostAddress();
			System.out.println("IP 주소 : " + ip);

			// 2. host 이름에 해당하는 ip정보를 가진 모든 inetAddress객체 얻기
			InetAddress[] ipArray = InetAddress.getAllByName("www.google.com");
			for (InetAddress tempip : ipArray) {
				System.out.println(tempip);
			}
			
			// 3. 현재 컴퓨터의 ip정보를 가진 inetAddress객체 얻기
			InetAddress myHost = InetAddress.getLocalHost();

			System.out.println("host : " + myHost.getHostName());
			System.out.println("host IP : " + myHost.getHostAddress());
		} catch (UnknownHostException e) {
			// host 이름에 해당하는 host를 찾지 못했을 경우 예외 처리
			e.printStackTrace();
		}
	}
}
