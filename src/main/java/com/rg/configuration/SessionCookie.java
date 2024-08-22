package com.rg.configuration;

//import jakarta.servlet.ServletContext;

//import jakarta.servlet.ServletContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.session.ExpiringSession;
//import org.springframework.session.SessionRepository;
//import org.springframework.session.web.http.CookieHttpSessionStrategy;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
//import org.springframework.session.web.http.SessionRepositoryFilter;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
public class SessionCookie {
	
	/*
	@Bean
	public <S extends ExpiringSession> SessionRepositoryFilter<? extends ExpiringSession> springSessionRepositoryFilter(SessionRepository<S> sessionRepository, ServletContext servletContext) {
	    SessionRepositoryFilter<S> sessionRepositoryFilter = new SessionRepositoryFilter<S>(sessionRepository);
	    sessionRepositoryFilter.setServletContext(servletContext);
	    CookieHttpSessionStrategy httpSessionStrategy = new CookieHttpSessionStrategy();
	    httpSessionStrategy.setCookieName("MY_SESSION_NAME");
	    httpSessionStrategy.setCookieName("MY_SESSION_NAME");
	    sessionRepositoryFilter.setHttpSessionStrategy(httpSessionStrategy);
	    return sessionRepositoryFilter;
	}
	*/
	
	@Bean
	public CookieSerializer cookieSerializer() {
		
		String hostname = "";

		try {
			hostname = InetAddress.getLocalHost().getHostName();
			System.out.println("HostName : " + InetAddress.getLocalHost().getHostName());
			System.out.println("<br/>");
			System.out.println("HostAddress : " + InetAddress.getLocalHost().getHostAddress());
		} catch ( UnknownHostException e ) {
			e.printStackTrace();
		}

		DefaultCookieSerializer serializer = new DefaultCookieSerializer();
		if ("ip-172-31-27-22".equals(hostname)) {
		//if ("jisblee.me".equals(hostname)) {
			serializer.setDomainName("jisblee.me");
		} else {
			serializer.setDomainNamePattern("^.+?\\.(\\w+\\.[a-z]+)$");
		}
		serializer.setCookieName("JSESSIONID");
		serializer.setCookiePath("/");
		return serializer;
	}
	
}
