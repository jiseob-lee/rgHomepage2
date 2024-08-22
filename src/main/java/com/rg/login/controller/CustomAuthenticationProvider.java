package com.rg.login.controller;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.rg.login.dto.CustomUserDetails;
import com.rg.login.service.LoginService;
import com.rg.util.SHA512;

@Primary
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider { 

	private final Logger logger = LogManager.getLogger(CustomAuthenticationProvider.class);

	@Autowired
	private LoginService loginService;
	
	//@Autowired(required = false)
    //private HttpServletRequest request;
	
	@Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    	
    	//HttpSession session = null;
    	
    	/*
    	RequestAttributes attribs = RequestContextHolder.getRequestAttributes();
    	HttpServletRequest request = null;
    	if (RequestContextHolder.getRequestAttributes() != null) {
    	    request = ((ServletRequestAttributes) attribs).getRequest();
    	    session = request.getSession();
    	}
    	*/
    	
    	//if (request != null) {
    	    //session = request.getSession();
    	//}
    	
    	String userId = (String)authentication.getPrincipal();    
        String userPw = (String)authentication.getCredentials();
        String userPwEnc = SHA512.encrypt(userPw);
        
        logger.info("사용자가 입력한 로그인정보입니다. {}", userId + "/" + userPw);
        
        String id = loginService.loginProcess(userId, userPwEnc); 

        //if (userId.equals("test") && userPw.equals("test")) {
        if (id != null && !"".equals(id)) {

        	logger.info("정상 로그인입니다.");
        	
            List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();

            if ("rg".equals(id)) {
            	roles.add(new SimpleGrantedAuthority("ROLE_SUPER"));
            	roles.add(new SimpleGrantedAuthority("ROLE_DBA"));
            } else if ("yoon".equals(id)) {
            	roles.add(new SimpleGrantedAuthority("ROLE_MOM"));
            }
            
            roles.add(new SimpleGrantedAuthority("ROLE_USER"));
            

            UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(userId, userPw, roles);

            result.setDetails(new CustomUserDetails(userId, userPw));
            
            logger.info("정상 로그인입니다 2.");
            
            //if (session != null) {
            	//session.setAttribute("loginUserId1", userId);
            	//logger.info("#################### userId : " + userId);
            //}
            
            return result;

        } else {

        	logger.info("사용자 크리덴셜 정보가 틀립니다. 에러가 발생합니다.");

        	throw new BadCredentialsException("Bad credentials");
        }
    }
}