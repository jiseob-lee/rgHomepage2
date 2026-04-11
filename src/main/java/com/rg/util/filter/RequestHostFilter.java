package com.rg.util.filter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

public class RequestHostFilter implements Filter {

    static class FilteredRequest extends HttpServletRequestWrapper {

        static String host  = "";

        public FilteredRequest(ServletRequest request) {
        	super((HttpServletRequest)request);
        	HttpServletRequest req = (HttpServletRequest)request;
        	try {
				URL url = new URL(req.getRequestURL().toString());
				host  = url.getHost();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
        }

        /*
        public String sanitize(String input) {
            String result = "";
            for (int i = 0; i < input.length(); i++) {
                if (allowedChars.indexOf(input.charAt(i)) >= 0) {
                    result += input.charAt(i);
                }
            }
            return result;
        }
        */

        public String getParameter(String paramName) {
        	//System.out.println("##################################### paramName : " + paramName);
            String value = super.getParameter(paramName);
            if ("requestHost".equals(paramName)) {
            	//System.out.println("##################################### host paramName.");
            	value = host;
            }
            //System.out.println("##################################### getParameter.");
            //System.out.println("##################################### value : " + value);
            return value;
        }

        public String[] getParameterValues(String paramName) {
            String values[] = super.getParameterValues(paramName);
            if ("requestHost".equals(paramName)) {
            	values = new String[]{host};
            }
            //System.out.println("##################################### getParameterValues.");
            return values;
        }

		@Override
        public Map<String, String[]> getParameterMap() {
        	Map<String, String[]> map = new HashMap<>(super.getParameterMap());
        	map.put("requestHost", new String[] {host});
            return map;
        }

		@Override
        public Enumeration<String> getParameterNames() {
        	Vector<String> v1 = new Vector<String>();
        	v1.addElement("requestHost");
        	//출처: https://hyeonstorage.tistory.com/210 [개발이 하고 싶어요]
        	
        	Enumeration<String> e = super.getParameterNames();
        	
        	while (e.hasMoreElements()) {
        		v1.addElement(e.nextElement());
        	}
        	Enumeration<String> en = v1.elements();
            return en;
        }
        

    }

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//System.out.println("############################# RequestHostFilter.");
		chain.doFilter(new FilteredRequest((HttpServletRequest)request), response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
