package com.rg.attachment.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import jakarta.servlet.http.HttpServletRequest;

import com.rg.attachment.dto.AttachmentDTO;

public interface AttachmentService {

	public List<AttachmentDTO> getAttachmentList(AttachmentDTO attachmentDTO);
	
	public int getAttachmentListTotalCount(AttachmentDTO attachmentDTO);
	
	public AttachmentDTO insertAttachment(AttachmentDTO attachmentDTO, HttpServletRequest request);
	
	public int deleteAttachment(AttachmentDTO attachmentDTO);

	public void updateFileMapping(AttachmentDTO attachmentDTO);
	
	public static String getFileUploadPath(String boardIdx) {
		
		Properties properties = new Properties();
		
		String propertiesFile = "application.properties";
		
		String fileUploadPath = "";
		
        try {
			InputStream is = AttachmentService.class.getClassLoader().getResourceAsStream(propertiesFile);
			properties.load(is);
			fileUploadPath = properties.getProperty("fileUploadPath");
			if ("4".equals(boardIdx)) {
				fileUploadPath = properties.getProperty("customerUploadPath");
			} else if ("img".equals(boardIdx)) {
				fileUploadPath = properties.getProperty("imageUploadPath");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
        
		return fileUploadPath;
	}
	
	public AttachmentDTO getAttachmentDTOInfo(String attachmentIdx);
	
	public void checkAttachmentExists();
}
