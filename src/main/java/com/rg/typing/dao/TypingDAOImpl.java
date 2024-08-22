package com.rg.typing.dao;

import java.util.List;
import java.util.Map;

import jakarta.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rg.typing.dto.BibleDTO;
import com.rg.typing.dto.ChaptersTypedCount;
import com.rg.typing.dto.RecordCountDTO;
import com.rg.typing.dto.TemporaryTypingStorageDTO;
import com.rg.typing.dto.TypingIntroDTO;
import com.rg.typing.dto.TypingParamDTO;

@Repository("typingDAO")
public class TypingDAOImpl implements TypingDAO {

	private final Logger logger = LogManager.getLogger(TypingDAOImpl.class);

	@Autowired
	@Resource(name="sqlSessionTemplate")
	private SqlSession sqlSession;
	
	private final String namespace = "com.rg.typing.TypingMapper";

	@Override
	public void insertTypingRecord(TypingParamDTO typingParamDTO) {
		sqlSession.insert(namespace + ".insertTypingRecord", typingParamDTO);
	}

	@Override
	public List<TypingParamDTO> getTypingRecord(TypingParamDTO typingParamDTO) {
		return sqlSession.selectList(namespace + ".getTypingRecord", typingParamDTO);
	}

	@Override
	public List<BibleDTO> getBibleList(String loginId) {
		return sqlSession.selectList(namespace + ".getBibleList", loginId);
	}

	@Override
	public TemporaryTypingStorageDTO getTemporaryTypingStorage(TemporaryTypingStorageDTO temporaryTypingStorageDTO) {
		return sqlSession.selectOne(namespace + ".getTemporaryTypingStorage", temporaryTypingStorageDTO);
	}

	@Override
	public void putTemporaryTypingStorage(TemporaryTypingStorageDTO temporaryTypingStorageDTO) {
		sqlSession.update(namespace + ".putTemporaryTypingStorage", temporaryTypingStorageDTO);
	}

	@Override
	public void createTemporaryTypingStorage(TemporaryTypingStorageDTO temporaryTypingStorageDTO) {
		sqlSession.insert(namespace + ".createTemporaryTypingStorage", temporaryTypingStorageDTO);
	}

	@Override
	public void deleteTemporaryTypingStorage(TemporaryTypingStorageDTO temporaryTypingStorageDTO) {
		sqlSession.insert(namespace + ".deleteTemporaryTypingStorage", temporaryTypingStorageDTO);
	}

	@Override
	public List<RecordCountDTO> getRecordCountList(RecordCountDTO recordCountDTO) {
		return sqlSession.selectList(namespace + ".getRecordCountList", recordCountDTO);
	}

	@Override
	public List<TypingIntroDTO> getTypingIntro(String loginId) {
		return sqlSession.selectList(namespace + ".getTypingIntro", loginId);
	}

	@Override
	public List<ChaptersTypedCount> getChaptersTypedCount(String loginId) {
		return sqlSession.selectList(namespace + ".getChaptersTypedCount", loginId);
	}

	@Override
	public List<Integer> getDoingChapter(Map<String, String> map) {
		return sqlSession.selectList(namespace + ".getDoingChapter", map);
	}

	@Override
	public List<Integer> getNewChapter(Map<String, Object> map) {
		return sqlSession.selectList(namespace + ".getNewChapter", map);
	}
	
	@Override
	public Integer getChapterCount(Map<String, String> map) {
		return sqlSession.selectOne(namespace + ".getChapterCount", map);
	}

	@Override
	public void deleteTyping(TypingParamDTO typingParamDTO) {
		sqlSession.update(namespace + ".deleteTyping", typingParamDTO);
	}
	
}
