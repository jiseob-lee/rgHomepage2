package com.rg.typing.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rg.typing.dto.BibleDTO;
import com.rg.typing.dto.Pair;
import com.rg.typing.dto.RecordCountDTO;
import com.rg.typing.dto.TypingParamDTO;
import com.rg.typing.dto.TemporaryTypingStorageDTO;
import com.rg.typing.dto.TypingIntroDTO;
import com.rg.typing.service.TypingService;

@Controller
public class TypingController {

	private final Logger logger = LogManager.getLogger(TypingController.class);

	private String typingFileDirectory = "/home/ubuntu/typing";

	@Autowired
	private TypingService typingService;
	
	@RequestMapping(value = "/rg/getTypingFileList.do")
	@ResponseBody
	public List<String> getTypingFileList(HttpServletRequest request) {

		List<String> list = new ArrayList<String>();

		File folder = new File(typingFileDirectory);
		File[] files = folder.listFiles();

		if (files != null) { // some JVMs return null for empty dirs
			
			// Obtain the array of (file, timestamp) pairs.
			Pair[] pairs = new Pair[files.length];
			for (int i = 0; i < files.length; i++) {
			    pairs[i] = new Pair(files[i]);
			}
	
			// Sort them by timestamp.
			Arrays.sort(pairs);

			for (Pair p : pairs) {
				if (p.f.isDirectory()) {
					;
				} else {
					list.add(p.f.getName().substring(0, p.f.getName().lastIndexOf(".")));
				}
			}
		}
		
		return list;
	}

	
	/*
	 * /rg/getTypingContent.do?
	 * bibleName=" + encodeURIComponent(bibleName) 
	 * + "&bibleChapters=" + bibleChapters 
	 * + "&abbreviationKor=" + encodeURIComponent(abbreviationKor) 
	 * + "&abbreviationEng=" + abbreviationEng
	 */
	
	@RequestMapping(value = "/rg/getTypingContent.do", method=RequestMethod.POST)
	@ResponseBody
	public List<String> getTypingContent(HttpServletRequest request) {
		
		List<String> list = new ArrayList<String>();
		
		logger.debug("################################################### request.getRemoteHost : " + request.getRemoteHost());
		
		String ip2 = request.getHeader("X-Forwarded-For") == null ? request.getRemoteAddr() : request.getHeader("X-Forwarded-For");
		
		if (ip2.equals("127.0.0.1") || ip2.equals("0:0:0:0:0:0:0:1")) {
			typingFileDirectory = "E:\\eclipse-workspace\\rg-aws\\resources\\typing";
		}
		
		String FILENAME = typingFileDirectory + "/" + request.getParameter("abbreviationEng") + " - " + request.getParameter("bibleName") + ".txt";

		String prefix = request.getParameter("abbreviationKor") + " " + request.getParameter("bibleChapters") + ":";
		
		/*
		String[] arr = {"utf-8", "euc-kr", "ksc5601", "x-windows-949", "iso-8859-1"};
		
		for (int i=0; i<arr.length; i++) {
			for (int j=0; j<arr.length; j++) {
				try {
					logger.debug(arr[i] + "-" + arr[j] + " : " + new String(request.getParameter("item").getBytes(arr[i]), arr[j]));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		*/
		
		logger.debug("###### FILENAME : " + FILENAME);
		File f = new File(FILENAME);
		if (f.exists()) {
			logger.debug("###### FILE exists.");
		} else {
			logger.debug("###### FILENAME not exists.");
		}
		BufferedReader br = null;
		FileReader fr = null;

		try {

			// br = new BufferedReader(new FileReader(FILENAME));
			fr = new FileReader(FILENAME);
			//new FileInputStream(FILENAME);
			br = new BufferedReader(new InputStreamReader(new FileInputStream(FILENAME),"UTF8"));
			//br = new BufferedReader(fr);

			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null) {
				logger.debug(sCurrentLine);
				if (!"".equals(sCurrentLine.trim()) && sCurrentLine.trim().startsWith(prefix)) {
					List<String> l = splitBySomeLength(sCurrentLine.trim().substring(prefix.length()));
					if (l != null) {
						for (int i=0; i < l.size(); i++) {
							list.add(l.get(i));
						}
					}
					//list.add(sCurrentLine.trim().substring(prefix.length()));
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (fr != null) {
					fr.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		return list;
	}

	@RequestMapping(value = "/rg/putTypingRecord.do", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> putTypingRecord(HttpServletRequest request, TypingParamDTO typingParamDTO) {
		
		logger.info("preTyped (putTypingRecord) : " + request.getSession().getAttribute("preTyped"));
		
		Map<String, String> map = new HashMap<>();
		
		if (request.getSession().getAttribute("preTyped") != null 
				&& "1".equals(request.getSession().getAttribute("preTyped"))) {
			
			map.put("preTyped", "1");
			
			return map;
		
		} else {
			
			logger.info("11111 insertTypingRecord.");
			
			String resultString = typingService.insertTypingRecord(typingParamDTO, request);
			
			map.put("resultString", resultString);
			
			return map;
		}
	}

	@RequestMapping(value = "/rg/deleteTyping.do", method=RequestMethod.POST)
	@ResponseBody
	public String deleteTyping(HttpServletRequest request, TypingParamDTO typingParamDTO) {
		typingService.deleteTyping(typingParamDTO, request);
		return "0";
	}
		
	@RequestMapping(value = "/rg/getTypingRecord.do", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getTypingRecord(HttpServletRequest request, TypingParamDTO typingParamDTO) {
		return typingService.getTypingRecord(typingParamDTO);
	}
	
	@RequestMapping(value = "/rg/getBibleList.do")
	@ResponseBody
	public List<BibleDTO> getBibleList(HttpServletRequest request) {
		return typingService.getBibleList((String)request.getSession().getAttribute("loginId"));
	}

	@RequestMapping(value = "/rg/putTemporaryTypingStorage.do", method=RequestMethod.POST)
	@ResponseBody
	public void putTemporaryTypingStorage(HttpServletRequest request, 
			TemporaryTypingStorageDTO temporaryTypingStorageDTO) {
		
		typingService.putTemporaryTypingStorage(temporaryTypingStorageDTO);
	}

	@RequestMapping(value = "/rg/getTemporaryTypingStorage.do")
	@ResponseBody
	public TemporaryTypingStorageDTO getTemporaryTypingStorage(HttpServletRequest request, 
			TemporaryTypingStorageDTO temporaryTypingStorageDTO) {
		
		return typingService.getTemporaryTypingStorage(temporaryTypingStorageDTO, request);
	}
		
	//@Test
	public List<String> splitBySomeLength(String str) {
		
		String str1 = "가나다 라마바사아 자차카 타파하가 나다라마바사 아자차카타파하가 나다라마 바사아자차카타 파하가 나다 라마바사 아자차카타파 하";
		
		if (str == null) {
			str = str1;
			return null;
		}
		
		while (str.indexOf("  ") > -1) {
			str = str.replaceAll("  ", " ");
		}
		
		String[] arr = str.split(" ");
		String temp = "";
		//int len = 0;
		int someLength = 42;
		List<String> result = new ArrayList<String>();
		
		for (int i=0; i < arr.length; i++) {
			if (temp.length() + arr[i].length() >= someLength) {
				result.add(temp);
				temp = arr[i];
			} else {
				temp += (temp.length() > 0 ? " " : "") + arr[i];
			}
		}
		
		if (temp.length() > 0) {
			result.add(temp);
		}
		
		//for (int i=0; i < result.size(); i++) {
			//logger.debug(result.get(i));
		//}
		
		return result;
	}

	@RequestMapping(value = "/rg/getRecordCountList.do")
	@ResponseBody
	public Map<String, Integer> getRecordCountList(HttpServletRequest request, RecordCountDTO recordCountDTO) {
		return typingService.getRecordCountList(recordCountDTO);
	}

	//@RequestMapping(value = "/rg/getTypingIntro.do", method=RequestMethod.POST)
	@RequestMapping(value = "/rg/getTypingIntro.do")
	@ResponseBody
	public List<TypingIntroDTO> getTypingIntro(HttpServletRequest request, RecordCountDTO recordCountDTO) {
		return typingService.getTypingIntro(request.getParameter("loginId"));
	}

	@RequestMapping(value = "/rg/getDoingChapter.do", method=RequestMethod.POST)
	@ResponseBody
	public Integer getDoingChapter(HttpServletRequest request, RecordCountDTO recordCountDTO) {
		
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("userId", request.getParameter("userId"));
		map.put("phrase", request.getParameter("phrase"));
		map.put("bibleChapters", request.getParameter("bibleChapters"));
		
		return typingService.getDoingChapter(map);
	}

}
