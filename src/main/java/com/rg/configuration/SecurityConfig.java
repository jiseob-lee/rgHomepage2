package com.rg.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

//@Configuration
//@EnableWebSecurity
public class SecurityConfig {
/*
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
    	
    	MvcRequestMatcher.Builder mvc = new MvcRequestMatcher.Builder(introspector).servletPath("/");
    	
        http
            .authorizeHttpRequests((authz) -> authz
                .requestMatchers(mvc.pattern("/assets/**"), mvc.pattern("/src/**"), mvc.pattern("index.jsp"), mvc.pattern("index.html")).permitAll() // "/public/**" 경로는 인증 없이 접근 허용
                .requestMatchers(mvc.pattern("/rg/**")).hasRole("USER") // "/admin/**" 경로는 "ADMIN" 역할을 가진 사용자만 접근 허용
                .anyRequest().permitAll() // 그 외 모든 요청은 인증된 사용자만 접근 가능
            )
            .formLogin(form -> form // 폼 기반 로그인 설정
                .loginPage("/login") // 로그인 페이지 URL
                .permitAll() // 로그인 페이지는 인증 없이 접근 허용
            )
            .logout(logout -> logout // 로그아웃 설정
                .permitAll() // 로그아웃은 인증 없이 접근 허용
            );
        return http.build();
    }
*/
}