package com.rg.configuration;

//import java.util.Map;
//import java.util.Map.Entry;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
//import org.springframework.stereotype.Component;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
//import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
//import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
//import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
//import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

//@Controller
//@Component
public class AuthInterceptor implements HandlerInterceptor {
//public class AuthInterceptor extends HandlerInterceptorAdapter {
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		System.out.println("###### Inside pre handle");
		return true;
	}

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("###### Inside post handle");
	}

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception exception)
			throws Exception {
		System.out.println("###### Inside after completion");
	}
	//private static final Logger LOG = LoggerFactory.getLogger(AuthInterceptor.class);

	//@Autowired
	//EndpointDocSVC endpointDocSVC;

	//@Autowired
	//private RequestMappingHandlerMapping requestMappingHandlerMapping;
	
	/*
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// Controller 및 View(jsp) 까지 모두 호출 된 후에 수행 됩니다.
		System.out.println("############ afterCompletion ############");
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// Controller가 호출 되어 수행되고, View(jsp) 호출되기전에 수행 됩니다.
		System.out.println("############ postHandle ############");
	}

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
		// 제일 많이 쓰이며, Controller 가 수행되기전에 수행 됩니다.

		//String endPoint = "";
		System.out.println("############ preHandle ############");
		//LOG.info("#### req.getRequestURI() : " + req.getRequestURI());
		
		
		try {
			Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();

		    for (Entry<RequestMappingInfo, HandlerMethod> item : map.entrySet()) {
		        RequestMappingInfo mapping = item.getKey();
		        //HandlerMethod method = item.getValue();

		        PatternsRequestCondition condition = mapping.getPatternsCondition().getMatchingCondition(req);
				if (condition != null) {
					LOG.info("#### condition : " + condition.toString());
					endPoint = condition.toString().substring(1, condition.toString().length() - 1);
					if (endPoint.endsWith(".*")) {
						endPoint = endPoint.substring(0, endPoint.length() - 2);
					}
					LOG.info("#### endPoint : " + endPoint);
					break;
				}
		    }
		    
		    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		    
			// 세션에서 Auth 정보를 가져와 권한을 체크한다.
			if (authentication != null
					&& !authentication.getPrincipal().equals("anonymousUser")) {
				
				
				if (authentication.getDetails() != null) {

					CustomUserDetails userInfo = (CustomUserDetails) authentication.getDetails();

					////LOG.info("#### userInfo : " + userInfo.toString());
					
					boolean priviledge = false;
					
					if (!req.getRequestURI().endsWith("/adcommon/noPriviledge.do")) {
						priviledge = endpointDocSVC.checkPriviledge(userInfo.getAuthSeq(), endPoint);
					}
					
					if (!req.getRequestURI().endsWith("/adcommon/noPriviledge.do") & !priviledge) {
						
						String url = "";
						switch (req.getServerName()) {
						case "sfm-bof.shilladfs.com" :
							url = "https://sfm-bof.shilladfs.com";
							break;
						case "sfm.shilladfs.com/" :
							url = "https://sfm.shilladfs.com/";
							break;
						case "adadev.shillaintra.com" :
							url = "https://adadev.shillaintra.com:9443";
							break;
						case "adfdev.shillaintra.com" :
							url = "https://adfdev.shillaintra.com";
							break;
						case "localhost" :
							url = "http://localhost:" + req.getServerPort();
							break;
						default :
							url = "http://" + req.getServerName() + ":" + req.getServerPort();
							break;
						}
						
						res.sendRedirect(url + req.getContextPath() + "/adcommon/noPriviledge.do");
						return false;
					}
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
				
		return true;
	}
	*/
}
