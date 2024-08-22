package com.rg.attachment.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rg.attachment.dto.AttachmentDTO;

@Repository("attachmentDAO")
public class AttachmentDAOImpl implements AttachmentDAO {

	private final Logger logger = LogManager.getLogger(AttachmentDAOImpl.class);

	@Autowired
	@Resource(name="sqlSessionTemplate")
	private SqlSession sqlSession;
	
	private final String namespace = "com.rg.attachment.AttachmentMapper";

	@Override
	public List<AttachmentDTO> getAttachmentList(AttachmentDTO attachmentDTO) {
		return sqlSession.selectList(namespace + ".getAttachmentList", attachmentDTO);
	}

	@Override
	public AttachmentDTO insertAttachment(AttachmentDTO attachmentDTO) {
		sqlSession.insert(namespace + ".insertAttachment", attachmentDTO);
		return attachmentDTO;
	}

	@Override
	public int deleteAttachment(AttachmentDTO attachmentDTO) {
		return sqlSession.update(namespace + ".deleteAttachment", attachmentDTO);
	}
	
	@Override
	public AttachmentDTO getAttachmentDTOInfo(String attachmentIdx) {
		return sqlSession.selectOne(namespace + ".getAttachmentDTOInfo", attachmentIdx);
	}

	@Override
	public int getAttachmentListTotalCount(AttachmentDTO attachmentDTO) {
		return sqlSession.selectOne(namespace + ".getAttachmentListTotalCount", attachmentDTO);
	}

	@Override
	public void updateFileExists(List<String> list) {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("list", list);
		sqlSession.update(namespace + ".updateFileExists", map);
	}
	
	@Override
	public void updateFileNotExists(List<String> list) {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("list", list);
		sqlSession.update(namespace + ".updateFileNotExists", map);
	}

	@Override
	public void updateFileMapping(AttachmentDTO attachmentDTO) {
		sqlSession.update(namespace + ".updateFileMapping", attachmentDTO);
	}

	@Override
	public int getMaxAttachmentOrder(AttachmentDTO attachmentDTO) {
		if (sqlSession == null) {
			logger.debug("########################### sqlSession is null. #####################");
		}
		logger.debug("########################### boardIdx : " + attachmentDTO.getBoardIdx() + " #####################");
		logger.debug("########################### boardArticleIdx : " + attachmentDTO.getBoardArticleIdx() + " #####################");
		return sqlSession.selectOne(namespace + ".getMaxAttachmentOrder", attachmentDTO);
	}
}
