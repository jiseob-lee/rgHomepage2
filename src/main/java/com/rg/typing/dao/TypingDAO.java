package com.rg.typing.dao;

import java.util.List;
import java.util.Map;

import com.rg.typing.dto.BibleDTO;
import com.rg.typing.dto.ChaptersTypedCount;
import com.rg.typing.dto.RecordCountDTO;
import com.rg.typing.dto.TemporaryTypingStorageDTO;
import com.rg.typing.dto.TypingIntroDTO;
import com.rg.typing.dto.TypingParamDTO;

public interface TypingDAO {

	public void insertTypingRecord(TypingParamDTO typingParamDTO);
	
	public List<TypingParamDTO> getTypingRecord(TypingParamDTO typingParamDTO);

	public List<BibleDTO> getBibleList(String loginId);

	public TemporaryTypingStorageDTO getTemporaryTypingStorage(TemporaryTypingStorageDTO temporaryTypingStorageDTO);

	public void putTemporaryTypingStorage(TemporaryTypingStorageDTO temporaryTypingStorageDTO);
	
	public void createTemporaryTypingStorage(TemporaryTypingStorageDTO temporaryTypingStorageDTO);
	
	public void deleteTemporaryTypingStorage(TemporaryTypingStorageDTO temporaryTypingStorageDTO);
	
	public List<RecordCountDTO> getRecordCountList(RecordCountDTO recordCountDTO);
	
	public List<TypingIntroDTO> getTypingIntro(String loginId);
	
	public List<ChaptersTypedCount> getChaptersTypedCount(String loginId);
	
	public List<Integer> getDoingChapter(Map<String, String> map);
	
	public List<Integer> getNewChapter(Map<String, Object> map);
	
	public Integer getChapterCount(Map<String, String> map);
	
	public void deleteTyping(TypingParamDTO typingParamDTO);
	
}
