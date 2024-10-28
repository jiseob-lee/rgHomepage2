package com.rg.exception.resolver;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.rg.exception.dao.ExceptionDAOImpl;
import com.rg.exception.dto.ExceptionDTO;
import com.rg.util.IP;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DefaultExceptionResolver implements HandlerExceptionResolver {
	
	private final Logger logger = LogManager.getLogger(DefaultExceptionResolver.class);
	
	@Autowired
	@Resource(name="exceptionDAO")
	private ExceptionDAOImpl exceptionDAO;
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		
		HandlerMethod handlerMethod = (HandlerMethod) handler;

		String errorMsg = "";
		
		if (ex instanceof java.lang.NullPointerException) {
			errorMsg = "java.lang.NullPointerException";
		} else {
			errorMsg = findCauseUsingPlainJava(ex).getMessage();
			if (errorMsg == null || "".equals(errorMsg)) {
				errorMsg = ex.getMessage();
			}
		}

		Map<String, Object> errorMap = new HashMap<>();
		errorMap.put("CLASS_NM", handlerMethod.getBeanType().getCanonicalName());
		errorMap.put("METHOD_NM", handlerMethod.getMethod().getName());
		errorMap.put("ERROR_MSG", errorMsg);
		//StackTraceElement[] st = ex.getStackTrace();
		StackTraceElement[] st = findCauseUsingPlainJava(ex).getStackTrace();
		StringJoiner strJoiner = new StringJoiner("\n");
		for (StackTraceElement ste : st) {
			strJoiner.add(ste.toString());
		}
		
		
		errorMap.put("STACKTRACE", strJoiner.toString());

		Map<String,String[]> paramMap = request.getParameterMap();
		String formData = new Gson().toJson(paramMap); 
		errorMap.put("FORM_DATA", formData);
		

		try {
			errorLog(errorMap);
		} catch (Exception e1) {
			logger.info(e1.getMessage());
			e1.printStackTrace();
		}
		
		return null;
	}
	
	public Throwable findCauseUsingPlainJava(Throwable throwable) {
	    Objects.requireNonNull(throwable);
	    Throwable rootCause = throwable;
	    while (rootCause.getCause() != null && rootCause.getCause() != rootCause) {
	        rootCause = rootCause.getCause();
	    }
	    return rootCause;
	}

	private void errorLog(Map<String, Object> map) throws Exception {
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		
		String ip = IP.getClientIP(request);
		
		//map.put("REQUEST_URL", request.getRequestURI());
		//map.put("CLASS_NM", map.get("CLASS_NM"));
		//map.put("METHOD_NM", map.get("METHOD_NM"));
		//map.put("ERROR_MSG", map.get("ERROR_MSG") == null ? "" : map.get("ERROR_MSG"));
		//map.put("STACKTRACE", map.get("STACKTRACE"));
		//map.put("REQUEST_IP", ip);
		//map.put("FORM_DATA", map.get("FORM_DATA"));
		
		ExceptionDTO exceptionDTO = new ExceptionDTO();
		exceptionDTO.setRequestUrl(request.getRequestURI());
		exceptionDTO.setClassNm((String)map.get("CLASS_NM"));
		exceptionDTO.setMethodNm((String)map.get("METHOD_NM"));
		exceptionDTO.setErrorMsg(map.get("ERROR_MSG") == null ? "" : (String)map.get("ERROR_MSG"));
		exceptionDTO.setStacktrace((String)map.get("STACKTRACE"));
		exceptionDTO.setRequestIp(ip);
		exceptionDTO.setFormData((String)map.get("FORM_DATA"));
		
		exceptionDAO.insertException(exceptionDTO);
	}

}
