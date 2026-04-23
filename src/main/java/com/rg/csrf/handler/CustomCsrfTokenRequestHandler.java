package com.rg.csrf.handler;

import java.util.Collection;
import java.util.function.Supplier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRequestHandler;
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler;
import org.springframework.util.StringUtils;
import org.springframework.web.util.WebUtils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public final class CustomCsrfTokenRequestHandler implements CsrfTokenRequestHandler {

	private final Logger logger = LogManager.getLogger(CustomCsrfTokenRequestHandler.class);
	
    // 기본적으로 XOR 인코딩을 사용하는 핸들러를 위임 객체로 사용
    private final CsrfTokenRequestHandler delegate = new XorCsrfTokenRequestAttributeHandler();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, Supplier<CsrfToken> csrfToken) {
    	
    	Collection<String> setCookieHeaders = response.getHeaders("Set-Cookie");
    	for (String header : setCookieHeaders) {
    	    //System.out.println(header); // 예: "cookieName=value; Path=/; HttpOnly"
    	    logger.info("#### Set-Cookie : {}", header);
    	}
    	
        // 기존의 BREACH 방지 로직(XOR)을 유지
        this.delegate.handle(request, response, csrfToken);
    }

    @Override
    public String resolveCsrfTokenValue(HttpServletRequest request, CsrfToken csrfToken) {
        
    	
    	// 1. 헤더에서 토큰 추출 시도
        String actualToken = request.getHeader(csrfToken.getHeaderName());
        
        logger.info("#### HeaderName : {}", csrfToken.getHeaderName());
        logger.info("#### actualToken : {}", actualToken);
        
        String xsrfToken = "";
        Cookie myCookie = WebUtils.getCookie(request, "XSRF-TOKEN");
        if (myCookie != null) {
        	xsrfToken = myCookie.getValue();
        }
        
        if (StringUtils.hasText(actualToken)) {
            return actualToken;
        } else if (StringUtils.hasText(xsrfToken)) {
        	return xsrfToken;
        }
        
        // 2. 헤더에 없으면, delegate를 통해 파라미터 등에서 추출
        return this.delegate.resolveCsrfTokenValue(request, csrfToken);
    }
}
