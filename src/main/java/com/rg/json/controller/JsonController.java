package com.rg.json.controller;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@Controller
public class JsonController {

	private final Logger logger = LogManager.getLogger(JsonController.class);

	@RequestMapping(value = "/rg/json.do", method=RequestMethod.GET, produces={"application/json; charset=UTF-8"})
	@ResponseBody
	public String json(HttpServletResponse response, HttpServletRequest request) {
		
		//response.setCharacterEncoding("UTF-8");
		
		Locale locale = new Locale(
				request.getParameter("lang") == null ? "ko" : request.getParameter("lang"));
		
		ResourceBundle bundle = ResourceBundle.getBundle("message/message-common", locale);
		
		Map<String, String> bundleMap = resourceBundleToMap(bundle);

		Type mapType = new TypeToken<Map<String, String>>(){}.getType();

		String jsonBundle = new GsonBuilder()
		        .registerTypeAdapter(mapType, new BundleMapSerializer())
		        .create()
		        .toJson(bundleMap, mapType);
		
		return jsonBundle;
	}
	
	private Map<String, String> resourceBundleToMap(final ResourceBundle bundle) {
	    final Map<String, String> bundleMap = new HashMap<>();

	    for (String key: bundle.keySet()) {
	        final String value = bundle.getString(key);

	        bundleMap.put(key, value);
	    }

	    return bundleMap;
	}
}

