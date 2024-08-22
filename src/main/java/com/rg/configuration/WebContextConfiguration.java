package com.rg.configuration;

//import java.util.concurrent.Executor;

//import org.springframework.context.annotation.Bean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.core.Ordered;
//import org.springframework.web.servlet.HandlerMapping;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.multipart.MultipartResolver;
//import org.springframework.web.multipart.commons.CommonsMultipartResolver;
//import org.springframework.web.multipart.support.StandardServletMultipartResolver;
//import org.springframework.web.servlet.ViewResolver;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
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
public class WebContextConfiguration implements WebMvcConfigurer {

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
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //registry.addMapping("/**");
    	//registry.addMapping("/**").allowedOrigins("http://localhost", "http://localhost:8080", "http://localhost:8180").maxAge(3600);
    }
    
	//@Bean
	//public WebMvcConfigurer corsConfigurer() {
		//return new WebMvcConfigurer() {
			//@Override
			//public void addCorsMappings(CorsRegistry registry) {
				//registry.addMapping("/**").allowedOrigins("http://localhost");
			//}
		//};
	//}
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