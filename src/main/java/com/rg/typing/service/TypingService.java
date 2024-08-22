package com.rg.typing.service;

import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import com.rg.typing.dto.BibleDTO;
import com.rg.typing.dto.RecordCountDTO;
import com.rg.typing.dto.TemporaryTypingStorageDTO;
import com.rg.typing.dto.TypingIntroDTO;
import com.rg.typing.dto.TypingParamDTO;

public interface TypingService {

	public String insertTypingRecord(TypingParamDTO typingParamDTO, HttpServletRequest request);
	
	public Map<String, Object> getTypingRecord(TypingParamDTO typingParamDTO);
	
	public List<BibleDTO> getBibleList(String loginId);

	public TemporaryTypingStorageDTO getTemporaryTypingStorage(TemporaryTypingStorageDTO temporaryTypingStorageDTO, HttpServletRequest request);

	public void putTemporaryTypingStorage(TemporaryTypingStorageDTO temporaryTypingStorageDTO);
	
	public Map<String, Integer> getRecordCountList(RecordCountDTO recordCountDTO);
	
	public List<TypingIntroDTO> getTypingIntro(String loginId);
	
	public Integer getDoingChapter(Map<String, String> map);
	
	public void deleteTyping(TypingParamDTO typingParamDTO, HttpServletRequest request);
	
}
