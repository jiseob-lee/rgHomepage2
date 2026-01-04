package com.rg.configuration;

import java.time.Duration;
import java.util.Locale;

import org.springframework.context.annotation.Bean;

//import java.util.concurrent.Executor;

//import org.springframework.context.annotation.Bean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;

import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//import org.springframework.web.servlet.view.InternalResourceViewResolver;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//import org.springframework.web.servlet.handler.MappedInterceptor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;

@Configuration
//@EnableWebMvc
//@ComponentScan(basePackages = {"com.rg"})
//public class WebContextConfiguration {
//public class WebContextConfiguration implements WebMvcConfigurer {
public class WebContextConfiguration {

	/**
	 * 뷰 리졸버를 설정한다.
	 * @return
	 */
    /*
	@Bean
    public ViewResolver getViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/jsp/");
        resolver.setSuffix(".jsp");
        return resolver;
    }
    
    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver createMultipartResolver() {
        CommonsMultipartResolver resolver=new CommonsMultipartResolver();
        resolver.setDefaultEncoding("utf-8");
        return resolver;
    }
    */
    
	//@Autowired
	//AuthInterceptor authInterceptor;

    //@Bean
    //public MappedInterceptor authInterceptor() {
    	//System.out.println("############ addInterceptors ############");
        //return new MappedInterceptor(new String[]{"/**"}, authInterceptor);
    //}
	
	//@Bean
	//public HandlerMapping handlerMapping() {
	  //기본전략인 HandlerMapping을 빈으로 등록.
	  //RequestMappingHandlerMapping handlerMapping = new RequestMappingHandlerMapping();
	  //handlerMapping.setInterceptors();
	  //handlerMapping.setOrder(Ordered.HIGHEST_PRECEDENCE);    //우선순위 결정
	  //return handlerMapping;
	//}
	
	//@Bean
	//public RequestMappingHandlerMapping requestMappingHandlerMapping;
	
    //@Bean
    //public AuthInterceptor requestHandler() {
    	//System.out.println("############ requestHandler ############");
        //return new AuthInterceptor();
    //}
    
    //@Override
    //public void addInterceptors(InterceptorRegistry registry) {
    	//System.out.println("############ addInterceptors ############");
        //registry.addInterceptor(requestHandler()).addPathPatterns("/**");//.addPathPatterns("/**/*.do");//.excludePathPatterns("/user/**");
    	//registry.addInterceptor(new AuthInterceptor()).addPathPatterns("/**/*.do");
    //}
    
    //@Override
    //public void addCorsMappings(CorsRegistry registry) {
        //registry.addMapping("/**");
    	//registry.addMapping("/**").allowedOrigins("http://localhost", "http://localhost:8080", "http://localhost:8180").maxAge(3600);
    //}
    
	//@Bean
	//public WebMvcConfigurer corsConfigurer() {
		//return new WebMvcConfigurer() {
			//@Override
			//public void addCorsMappings(CorsRegistry registry) {
				//registry.addMapping("/**").allowedOrigins("http://localhost");
			//}
		//};
	//}
    
    
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }
    
    
    @Bean
    public LocaleResolver localeResolver() {
    	CookieLocaleResolver resolver = new CookieLocaleResolver();
        //resolver.setCookieName("lang");
        resolver.setCookieMaxAge(Duration.ofDays(30));   // 👈 여기
        resolver.setCookiePath("/");
        resolver.setDefaultLocale(Locale.FRENCH);

        return resolver;
    }
}

//@Configuration
//public class WebContextConfiguration implements WebMvcConfigurer {

	//@Autowired
	//@Qualifier(value = "httpInterceptor")
	//private HandlerInterceptor interceptor;

	//@Override
	//public void addInterceptors(InterceptorRegistry registry) {
		//System.out.println("======== addInterceptors =======");
		//registry.addInterceptor(interceptor)
				//.addPathPatterns("/**");
				//.excludePathPatterns("/user/**");
	//}
//}