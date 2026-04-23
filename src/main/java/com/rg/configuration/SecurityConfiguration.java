package com.rg.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import com.rg.csrf.handler.CustomCsrfTokenRequestHandler;
import com.rg.login.controller.CustomAuthenticationProvider;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    //@Bean
    //public WebSecurityCustomizer webSecurityCustomizer() {
        //return (web) -> web.ignoring().requestMatchers("/assets/**", "/src/**", "index.jsp", "index.html");
    //}

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   AuthenticationSuccessHandler loginSuccessHandler,
                                                   LogoutSuccessHandler logoutSuccessHandler,
                                                   SessionRegistry sessionRegistry) throws Exception {
    	
    	//MvcRequestMatcher.Builder mvc = new MvcRequestMatcher.Builder(introspector).servletPath("/");
        
    	//HttpSessionCsrfTokenRepository repo = new HttpSessionCsrfTokenRepository();
        //repo.setHeaderName("X-CSRF-TOKEN");
        //repo.setParameterName("_csrf");
        
        //CookieCsrfTokenRepository repo = CookieCsrfTokenRepository.withHttpOnlyFalse();
        //repo.setCookiePath("/");
        //repo.setHeaderName("X-XSRF-TOKEN");

        //XorCsrfTokenRequestAttributeHandler delegate = new XorCsrfTokenRequestAttributeHandler();
        //delegate.setCsrfRequestAttributeName("_csrf");

        
        http
        	//.csrf(csrf -> csrf.disable())
        	.csrf(csrf -> csrf
        		//.csrfTokenRepository(repo)
        		//.csrfTokenRepository(repo)
        	    //.csrfTokenRequestHandler(delegate)
        	    .csrfTokenRequestHandler(new CustomCsrfTokenRequestHandler()) // 커스텀 핸들러 등록
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) // SPA용 쿠키 저장소
                .ignoringRequestMatchers("/getBoardContent.do")  // <-- CSRF 예외 등록
                .ignoringRequestMatchers("/getBoardListCount.do")
                .ignoringRequestMatchers("/createResponse.do")
                //.ignoringRequestMatchers("/inputComment.do")
            )

            .authorizeHttpRequests(auth -> auth
            	.requestMatchers("/assets/**", "/src/**", "/index.jsp", "/index.html").permitAll()
            	//.requestMatchers("/", "/**").permitAll()
            	.requestMatchers("/rg/**").hasRole("USER")
                .anyRequest().permitAll()
            )
            //.authorizeHttpRequests(auth -> auth
                    //.antMatchers("/api/**").permitAll()
                    //.antMatchers("/admin/**").hasRole("ADMIN")
                    //.anyRequest().authenticated()
                //)
            .formLogin(form -> form
                .loginPage("/login.do")
                .loginProcessingUrl("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .failureUrl("/login.do?error=true")
                .successHandler(loginSuccessHandler)
            )

            .logout(logout -> logout
                .logoutUrl("/logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .logoutSuccessHandler(logoutSuccessHandler)
            )

            .sessionManagement(session -> session
                .sessionFixation(sessionFixation -> sessionFixation.none())
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false)
                .expiredUrl("/login.do")
                .sessionRegistry(sessionRegistry)
            );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http,
                                                       AuthenticationProvider customAuthenticationProvider) throws Exception {
        return http
            .getSharedObject(AuthenticationManagerBuilder.class)
            .authenticationProvider(customAuthenticationProvider)
            .build();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    // Handler 및 Provider 등록 (Component Scan 가능하면 생략 가능)
    @Bean
    public AuthenticationProvider customAuthenticationProvider() {
        return new CustomAuthenticationProvider();
    }

    //@Bean
    //public AuthenticationSuccessHandler loginSuccessHandler() {
        //return new CustomLoginSuccessHandler();
    //}

    //@Bean
    //public LogoutSuccessHandler logoutSuccessHandler() {
        //return new CustomLogoutSuccessHandler();
    //}
    
}
