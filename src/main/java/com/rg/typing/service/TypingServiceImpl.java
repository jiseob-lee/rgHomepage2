package com.rg.typing.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rg.typing.dao.TypingDAOImpl;
import com.rg.typing.dto.BibleDTO;
import com.rg.typing.dto.ChaptersTypedCount;
import com.rg.typing.dto.RecordCountDTO;
import com.rg.typing.dto.TemporaryTypingStorageDTO;
import com.rg.typing.dto.TypingIntroDTO;
import com.rg.typing.dto.TypingParamDTO;

@Service("typingService")
public class TypingServiceImpl implements TypingService {

	private final Logger logger = LogManager.getLogger(TypingServiceImpl.class);
	
	@Autowired
	private TypingDAOImpl typingDAO;
	
	@Override
	public String insertTypingRecord(TypingParamDTO typingParamDTO, HttpServletRequest request) {
		
		String resultString = "";
		String tmpStr = "";
		
		try {
			TemporaryTypingStorageDTO temporaryTypingStorageDTO = new TemporaryTypingStorageDTO();
			temporaryTypingStorageDTO.setUserId(typingParamDTO.getLoginId());
			temporaryTypingStorageDTO.setPhrase(typingParamDTO.getPhrase());
			temporaryTypingStorageDTO.setChapter(typingParamDTO.getChapter());
			
			TemporaryTypingStorageDTO temporaryTypingStorageDTO2 = typingDAO.getTemporaryTypingStorage(temporaryTypingStorageDTO);
			
			if (temporaryTypingStorageDTO2 != null) {
				resultString = temporaryTypingStorageDTO2.getResultString();
			}
			
			for (int i=0; i < typingParamDTO.getTypingLength(); i++) {
				tmpStr += "1";
			}
			
			logger.debug("tmpStr" + tmpStr);
			logger.debug("ResultString : " + temporaryTypingStorageDTO2.getResultString());
			
			if (//resultString.equals(tmpStr)
					//&& 
					"0".equals(request.getSession().getAttribute("preTyped"))) {
				typingDAO.insertTypingRecord(typingParamDTO);	
			}
	
			//user_id = #{userId}
			//and phrase = #{phrase}
			//and chapter = #{chapter}
			
			//TemporaryTypingStorageDTO temporaryTypingStorageDTO = new TemporaryTypingStorageDTO();
			//temporaryTypingStorageDTO.setUserId(typingParamDTO.getLoginId());
			//temporaryTypingStorageDTO.setPhrase(typingParamDTO.getPhrase());
			//temporaryTypingStorageDTO.setChapter(typingParamDTO.getChapter());
			//typingDAO.deleteTemporaryTypingStorage(temporaryTypingStorageDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//return resultString;
		return tmpStr;
	}

	@Override
	public Map<String, Object> getTypingRecord(TypingParamDTO typingParamDTO) {

		Map<String, Object> map = new HashMap<String, Object>();
		
		int listLimit = 10;
		map.put("listLimit", listLimit);
		
		List<TypingParamDTO> list = typingDAO.getTypingRecord(typingParamDTO);
		
		map.put("totalCount", list.size());
		map.put("pageNo", typingParamDTO.getPageNo());
		
		List<TypingParamDTO> list2 = list.stream()
									.skip((typingParamDTO.getPageNo() - 1) * listLimit)
									.limit(listLimit)
									.collect(Collectors.toList());
		
		List<TypingParamDTO> list3 = new ArrayList<TypingParamDTO>();
		
		for (int i=0; i < list2.size(); i++) {
			
			TypingParamDTO typingDTO = list2.get(i);
			
			int msec = typingDTO.getTimeElapsed();
			//logger.debug("걸린 시간 : " + msec);
			Double hh = Math.floor(msec / 60 / 60);
			//logger.debug("시간 : " + hh);
			msec -= hh * 60 * 60;
			Double mm = Math.floor(msec / 60);
			//logger.debug("분 : " + mm);
			msec -= mm * 60;
			int ss = msec;
			//logger.debug("초 : " + ss);
			//msec -= ss * 1000;
			
			String timeMessage = "";//(hh > 0 ? hh.intValue() + "시간" : "") + " " + (mm > 0 ? mm.intValue() + "분" : "") + " " + (ss > 0 ? ss + "초" : "");

			if (hh > 0) {
				//logger.debug("hh > 0");
				timeMessage += hh.intValue() + "시간 ";
			} else {
				//logger.debug("hh <= 0");
			}

			if (mm > 0) {
				//logger.debug("mm > 0");
				timeMessage += mm.intValue() + "분 ";
			} else {
				//logger.debug("mm <= 0");
			}

			if (ss > 0) {
				//logger.debug("ss > 0");
				timeMessage += ss + "초";
			} else {
				//logger.debug("ss <= 0");
			}
			
			typingDTO.setTimeElapsedString(timeMessage);
			
			list3.add(typingDTO);
		}
		
		map.put("list", list3);
		
		return map;
	}

	@Override
	public List<BibleDTO> getBibleList(String loginId) {
		return typingDAO.getBibleList(loginId);
	}

	@Override
	public TemporaryTypingStorageDTO getTemporaryTypingStorage(TemporaryTypingStorageDTO temporaryTypingStorageDTO, HttpServletRequest request) {
		
		TemporaryTypingStorageDTO temporaryTypingStorageDTO2 = typingDAO.getTemporaryTypingStorage(temporaryTypingStorageDTO);
		
		if (temporaryTypingStorageDTO2 == null) {
			
			typingDAO.createTemporaryTypingStorage(temporaryTypingStorageDTO);
			
			request.getSession().setAttribute("preTyped", "0");
			
			logger.info("preTyped (createTemporaryTypingStorage) : " + request.getSession().getAttribute("preTyped"));
			
		} else {
			
			logger.info("ResultString (getTemporaryTypingStorage) : " + temporaryTypingStorageDTO2.getResultString());
			
			if (temporaryTypingStorageDTO2.getResultString() != null 
					&& temporaryTypingStorageDTO2.getResultString().indexOf("0") == -1
					&& temporaryTypingStorageDTO2.getResultString().indexOf("1") > -1) {
				
				request.getSession().setAttribute("preTyped", "1");
			
			} else {
				request.getSession().setAttribute("preTyped", "0");
			}
			
			logger.info("preTyped (getTemporaryTypingStorage) : " + request.getSession().getAttribute("preTyped"));
		}
		
		return temporaryTypingStorageDTO2;
	}

	@Override
	public void putTemporaryTypingStorage(TemporaryTypingStorageDTO temporaryTypingStorageDTO) {
		typingDAO.putTemporaryTypingStorage(temporaryTypingStorageDTO);
	}

	@Override
	public Map<String, Integer> getRecordCountList(RecordCountDTO recordCountDTO) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		
		List<RecordCountDTO> list = typingDAO.getRecordCountList(recordCountDTO);
		for (int i=0; i < list.size(); i++) {
			RecordCountDTO recordCount = list.get(i);
			map.put(recordCount.getTitle(), recordCount.getRecordCount());
		}
		
		return map;
	}

	@Override
	public List<TypingIntroDTO> getTypingIntro(String loginId) {
		
		List<TypingIntroDTO> listTypingIntroDTO = typingDAO.getTypingIntro(loginId);
		List<ChaptersTypedCount> listChaptersTypedCount = typingDAO.getChaptersTypedCount(loginId);

		//for (int l=0; l < listChaptersTypedCount.size(); l++) {
			//ChaptersTypedCount elem = listChaptersTypedCount.get(l);
			//logger.error(elem.toString());
		//}
		
		//logger.error("listTypingIntroDTO.size() : " + listTypingIntroDTO.size());
		
		for (int i=0; i < listTypingIntroDTO.size(); i++) {
			
			TypingIntroDTO typingIntroDTO = listTypingIntroDTO.get(i);
			Integer bibleChapters = typingIntroDTO.getBibleChapters();
			
			//logger.error("typingIntroDTO.getAbbreviationEng() : " + typingIntroDTO.getAbbreviationEng());
			
			List<ChaptersTypedCount> list = null;
			
			try {
				//listChaptersTypedCount.stream().forEach(t -> {System.out.println(t.getAbbreviationEng());});
				list = listChaptersTypedCount.stream()
					.filter(t -> typingIntroDTO.getAbbreviationEng().equals(t.getAbbreviationEng()))
					//.filter(t -> { System.out.println(t.getAbbreviationEng()); } )
					//.min(Comparator.comparing(ChaptersTypedCount::getCnt))
					//.orElse(null);
					.collect(Collectors.toList());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//int cnt = -1;
			ChaptersTypedCount chaptersTypedCount = null;
			
			if (list != null) {
				
				//logger.error("list.size() : " + list.size());
				
				//for (int l=0; l < list.size(); l++) {
					//ChaptersTypedCount elem = list.get(l);
					//logger.error(elem.getAbbreviationEng() + " : " + elem.getChapter() + " : " + elem.getCnt());
				//}
				
				//for (int j=0; j < bibleChapters; j++) {
					//for (int k=0; k < list.size(); k++) {
						//if (j == k) {
							//if (cnt == -1 || cnt > list.get(k).getCnt()) {
								//cnt = list.get(k).getCnt();
							//}
							//break;
						//}
					//}
				//}
				
				if (bibleChapters == list.stream().count()) {
					chaptersTypedCount = list.stream().min(Comparator.comparing(ChaptersTypedCount::getCnt)).orElse(null);
					//if (chaptersTypedCount != null) {
						//cnt = chaptersTypedCount.getCnt();
					//}
				}
			}
			
			if (chaptersTypedCount == null) {
				listTypingIntroDTO.get(i).setDoneCount(0);
			} else {
				listTypingIntroDTO.get(i).setDoneCount(chaptersTypedCount.getCnt());
			}
		}
		
		return listTypingIntroDTO;
	}

	@Override
	public Integer getDoingChapter(Map<String, String> map) {
		
		List<Integer> list = typingDAO.getDoingChapter(map);
		
		logger.debug("############################################### getDoingChapter size : " + list.size());
		logger.debug("############################################### phrase : " + map.get("phrase"));
		logger.debug("############################################### userId : " + map.get("userId"));
		logger.debug("############################################### loginId : " + map.get("loginId"));
		logger.debug("############################################### bibleChapters : " + map.get("bibleChapters"));

		if (list.size() > 0) {
			return list.get(0);
		} else {
			logger.debug("############################################### getNewChapter start.");
			//Integer chapterCount = typingDAO.getChapterCount(map);
			Integer chapterCount = Integer.valueOf(map.get("bibleChapters"));
			List<Integer> chapterList = new ArrayList<Integer>();
			for (int i=1; i <= chapterCount; i++) {
				chapterList.add(i);
			}
			
			Map<String, Object> map2 = new HashMap<String, Object>();
			map2.put("chapterList", chapterList);
			map2.put("loginId", map.get("userId"));
			map2.put("phrase", map.get("phrase"));
			List<Integer> list2 = typingDAO.getNewChapter(map2);
			if (list2.size() > 0) {
				return list2.get(0);
			} else {
				return 1;
			}
		}
	}
	
	@Override
	public void deleteTyping(TypingParamDTO typingParamDTO, HttpServletRequest request) {
		logger.debug("typingParamDTO : " + typingParamDTO.toString());
		typingDAO.deleteTyping(typingParamDTO);
		request.getSession().setAttribute("preTyped", "0");
	}
}
