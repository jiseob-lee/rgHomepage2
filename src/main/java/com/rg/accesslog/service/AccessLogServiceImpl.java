package com.rg.accesslog.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.rg.accesslog.dao.AccessLogDAOImpl;
import com.rg.accesslog.dto.BoardViewDTO;
import com.rg.util.GeoLite2;

@Service("accessLogService")
public class AccessLogServiceImpl implements AccessLogService {
	
	private final Logger logger = LogManager.getLogger(AccessLogServiceImpl.class);
	
	@Value("${apacheAccessLogPath}")
	private String logPath;
	
	@Autowired
	private AccessLogDAOImpl accessLogDAO;
	
	@Override
	public List<BoardViewDTO> getBoardViewList(String logDate) {
		
		File directory = new File(logPath);
		
		String[] fileNames = directory.list();
		
		List<String> filenameList = Arrays.asList(fileNames);
		

		List<BoardViewDTO> boardViewList = new ArrayList<>();
		
		int i = 0;
		
		//String filename = "jisblee.me-ssl-access_log." + logDate + "-00.log";
		
		String filename = String.format("jisblee.me-ssl-access_log." + logDate + "-%02d.log", i++);
		
		do {
			
			List<BoardViewDTO> list = parseBoardViewList(logPath + filename);
			
			boardViewList.addAll(list);
			
			filename = String.format("jisblee.me-ssl-access_log." + logDate + "-%02d.log", i++);
			
		} while (filenameList.contains(filename));
		
		
		return boardViewList;
	}
	
	private List<BoardViewDTO> parseBoardViewList(String filePath) {
		
		List<BoardViewDTO> list = new ArrayList<>();
		
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            
        	String line;
            
        	while ((line = reader.readLine()) != null) {
            	
            	if (line.indexOf("\"node\"") == -1) {
	            	
            		if (line.indexOf("GET /board/view/") > -1) {
	            		logger.debug(line); // Process each line here
	            		list.add(parseAccessLog(line));
	            	
	            	} else if (line.indexOf("GET /getBoardContent.do") > -1) {
	            		logger.debug(line); // Process each line here
	            		list.add(parseAccessLog2(line));
	            	}
            	}
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        
        return list;
	}
	
	private BoardViewDTO parseAccessLog(String logEntry) {
        //String logEntry = "66.249.70.164 - - [22/Jun/2025:19:31:50 +0900] \"GET /board/view/0/1/887 HTTP/1.1\" 200 14959 \"-\" \"Mozilla/5.0 (Linux; Android 6.0.1; Nexus 5X Build/MMB29P) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/137.0.7151.103 Mobile Safari/537.36 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)\"";

		BoardViewDTO dto = new BoardViewDTO();
		
        // Apache Combined Log Format
        String logPattern = 
                "^(\\S+) (\\S+) (\\S+) \\[([^\\]]+)] \"([A-Z]+) ([^ ]+) ([^\"]+)\" (\\d{3}) (\\d+|-) \"([^\"]*)\" \"([^\"]*)\"";

        Pattern pattern = Pattern.compile(logPattern);
        Matcher matcher = pattern.matcher(logEntry);

        if (matcher.find()) {
        	logger.debug("IP Address: " + matcher.group(1));
        	logger.debug("Date Time: " + matcher.group(4));
        	logger.debug("Method: " + matcher.group(5));
        	logger.debug("Request URI: " + matcher.group(6));
        	logger.debug("Protocol: " + matcher.group(7));
        	logger.debug("Status Code: " + matcher.group(8));
        	logger.debug("Response Size: " + matcher.group(9));
        	logger.debug("Referer: " + matcher.group(10));
        	logger.debug("User-Agent: " + matcher.group(11));
            
            dto.setIpAddress(matcher.group(1));
            dto.setDateTime(matcher.group(4));
            dto.setMethod(matcher.group(5));
            dto.setRequestURI(matcher.group(6));
            dto.setProtocol(matcher.group(7));
            dto.setStatusCode(matcher.group(8));
            dto.setResponseSize(matcher.group(9));
            dto.setReferer(matcher.group(10));
            dto.setUserAgent(matcher.group(11));
            
            Map<String, String> geoLiteMap = GeoLite2.getIpInfo(dto.getIpAddress());
            if (geoLiteMap != null) {
	            dto.setCountry(geoLiteMap.get("country"));
	            dto.setSubdivision(geoLiteMap.get("subdivision"));
	            dto.setCity(geoLiteMap.get("city"));
            }
            
            String[] arrStr = dto.getRequestURI().split("/");
            
            if (arrStr != null && arrStr.length > 5) {
	            String articleSubject = accessLogDAO.getArticleSubject(arrStr[5]);
	            dto.setArticleSubject(articleSubject);
            }
            
        } else {
        	logger.debug("No match found.");
            return null;
        }
        
        return dto;
	}

	
	private BoardViewDTO parseAccessLog2(String logEntry) {
        //String logEntry = "66.249.70.164 - - [22/Jun/2025:19:31:50 +0900] \"GET /board/view/0/1/887 HTTP/1.1\" 200 14959 \"-\" \"Mozilla/5.0 (Linux; Android 6.0.1; Nexus 5X Build/MMB29P) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/137.0.7151.103 Mobile Safari/537.36 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)\"";

		BoardViewDTO dto = new BoardViewDTO();
		
        // Apache Combined Log Format
        String logPattern = 
                "^(\\S+) (\\S+) (\\S+) \\[([^\\]]+)] \"([A-Z]+) ([^ ]+) ([^\"]+)\" (\\d{3}) (\\d+|-) \"([^\"]*)\" \"([^\"]*)\"";

        Pattern pattern = Pattern.compile(logPattern);
        Matcher matcher = pattern.matcher(logEntry);

        if (matcher.find()) {
        	logger.debug("IP Address: " + matcher.group(1));
        	logger.debug("Date Time: " + matcher.group(4));
        	logger.debug("Method: " + matcher.group(5));
        	logger.debug("Request URI: " + matcher.group(6));
        	logger.debug("Protocol: " + matcher.group(7));
        	logger.debug("Status Code: " + matcher.group(8));
        	logger.debug("Response Size: " + matcher.group(9));
        	logger.debug("Referer: " + matcher.group(10));
        	logger.debug("User-Agent: " + matcher.group(11));
            
            dto.setIpAddress(matcher.group(1));
            dto.setDateTime(matcher.group(4));
            dto.setMethod(matcher.group(5));
            dto.setRequestURI(matcher.group(6));
            dto.setProtocol(matcher.group(7));
            dto.setStatusCode(matcher.group(8));
            dto.setResponseSize(matcher.group(9));
            dto.setReferer(matcher.group(10));
            dto.setUserAgent(matcher.group(11));
            
            Map<String, String> geoLiteMap = GeoLite2.getIpInfo(dto.getIpAddress());
            if (geoLiteMap != null) {
	            dto.setCountry(geoLiteMap.get("country"));
	            dto.setSubdivision(geoLiteMap.get("subdivision"));
	            dto.setCity(geoLiteMap.get("city"));
            }
            
            String requestURI = dto.getRequestURI();
            
            String articleIdx = requestURI.indexOf("boardArticleIdx=") == -1 ? "0" : 
            	requestURI.substring(requestURI.indexOf("boardArticleIdx=") + "boardArticleIdx=".length(), requestURI.indexOf("&"));
            
            //if (arrStr != null && arrStr.length > 5) {
	            String articleSubject = accessLogDAO.getArticleSubject(articleIdx);
	            dto.setArticleSubject(articleSubject);
            //}
            
        } else {
        	logger.debug("No match found.");
            return null;
        }
        
        return dto;
	}


}
