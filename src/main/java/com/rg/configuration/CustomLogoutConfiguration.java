package com.rg.configuration;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import com.rg.util.RedisService3;

//@Configuration
//@EnableWebSecurity
public class CustomLogoutConfiguration {

	@Autowired
	private RedisService3 redisService;
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
          .logout()
          .logoutUrl("/logout")
          .addLogoutHandler(new LogoutHandler() {
              // handler 를 직접 작성해서 추가적인 로그아웃 처리가 가능.
              @Override
              public void logout(HttpServletRequest request, HttpServletResponse response,
                  Authentication authentication) {
                  System.out.println("Logout 처리 핸들러");
                  HttpSession session = request.getSession();
                  redisService.processRedisLogout("LOGIN||SESSION||" + (String)session.getAttribute("loginId") + "||" + session.getId());
                  session.invalidate();
              }
          })
          .logoutSuccessHandler(new LogoutSuccessHandler() {
              @Override
              public void onLogoutSuccess(HttpServletRequest request,
                  HttpServletResponse response, Authentication authentication)
                  throws IOException, ServletException {
                  System.out.println("로그아웃 이후");
                  response.sendRedirect("/");
              }
          });
          ;
        return http.build();
    }
}