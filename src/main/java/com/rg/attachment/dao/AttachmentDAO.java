package com.rg.attachment.dao;

import java.util.List;

import com.rg.attachment.dto.AttachmentDTO;

public interface AttachmentDAO {

	public List<AttachmentDTO> getAttachmentList(AttachmentDTO attachmentDTO);
	
	public AttachmentDTO insertAttachment(AttachmentDTO attachmentDTO);
	
	public int deleteAttachment(AttachmentDTO attachmentDTO);
	
	public AttachmentDTO getAttachmentDTOInfo(String attachmentIdx);
	
	public int getAttachmentListTotalCount(AttachmentDTO attachmentDTO);
	
	public void updateFileExists(List<String> list);
	
	public void updateFileNotExists(List<String> list);
	
	public void updateFileMapping(AttachmentDTO attachmentDTO);
	
	public int getMaxAttachmentOrder(AttachmentDTO attachmentDTO);
}
