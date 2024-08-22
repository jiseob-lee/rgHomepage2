package com.rg.attachment.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.rg.attachment.dto.AttachmentDTO;

@Service
public class AsyncService {
	
	private final Logger logger = LogManager.getLogger(AttachmentServiceImpl.class);
	
	@Async("threadPoolTaskExecutor")
	public void onAsyncTask(AttachmentDTO attachmentDTO, HttpServletRequest request, HttpServletResponse response) {

		logger.info("async task start");
		/*
		long sTime = System.currentTimeMillis();
		long eTime;
		try {

			Thread.sleep(processingTime);
			eTime = System.currentTimeMillis();
		} catch (InterruptedException e) {
			e.printStackTrace();
			eTime = System.currentTimeMillis();
		}
		*/

		//String idx = request.getParameter("idx");
		//String filename = request.getParameter("filename");
		String idx = attachmentDTO.getAttachmentIdx();
		String filename = attachmentDTO.getServerFileName();
		
		logger.info("##### idx 2 : " + idx);
		logger.info("##### filename 2 : " + filename);
		if (idx == null || "".equals(idx) || filename == null || "".equals(filename)) {
			logger.info("##### empty...");
			//return CompletableFuture.completedFuture(null);
			return;
		}

		String fileUploadPath = AttachmentService.getFileUploadPath("0");

		logger.info("##### fileUploadPath : " + fileUploadPath);
		
		//AttachmentDTO attachmentDTO = attachmentService.getAttachmentDTOInfo(idx);
		
		logger.info("##### filename 11 : " + filename);
		logger.info("##### filename 12 : " + attachmentDTO.getServerFileName());
		
		if (!filename.equals(attachmentDTO.getServerFileName())) {
			logger.error("############################");
			logger.error(filename + " : " + attachmentDTO.getServerFileName());
			//return CompletableFuture.completedFuture(null);
			return;
		}

		File f = new File(fileUploadPath + filename .substring(0, 4) + "/" + filename + "." + attachmentDTO.getAttachmentExt());

		logger.info("##### file : " + fileUploadPath + filename .substring(0, 4) + "/" + filename + "." + attachmentDTO.getAttachmentExt());

		//HttpServletResponseCopier response = null;
		//try {
			//response = new HttpServletResponseCopier((HttpServletResponse) response1);
		//} catch (IOException e1) {
			//e1.printStackTrace();
		//}

		if (f.exists()) {
			try {

				filename = attachmentDTO.getAttachmentName() + "." + attachmentDTO.getAttachmentExt();
				logger.info("##### filename : " + filename);
				//filename = URLEncoder.encode(filename, "UTF-8");
				//filename = filename.replaceAll("\\+", "%20");
				filename = getDisposition(filename, getBrowser(request));

				//byte fileByte[] = FileUtils.readFileToByteArray(f);
				
				response.setContentType("application/octet-stream");
				//response.setContentLength(fileByte.length);
				response.setContentLength(Long.valueOf(f.length()).intValue());
				response.setHeader("Content-Disposition", filename);
				response.setHeader("Content-Transfer-Encoding", "binary");
				//response.getOutputStream().write(fileByte);

				//response.getOutputStream().flush();
				//response.getOutputStream().close();
				
				
				ServletOutputStream out = response.getOutputStream();
			    FileInputStream fileInputStream  = new FileInputStream(f);
			    byte[] buf = new byte[8192];
			    int bytesread = 0, bytesBuffered = 0;
			    while( (bytesread = fileInputStream.read( buf )) > -1 ) {
			        out.write( buf, 0, bytesread );
			        bytesBuffered += bytesread;
			        if (bytesBuffered > 1024 * 1024) { //flush after 1MB
			            bytesBuffered = 0;
			            out.flush();
			        }
			    }
			    fileInputStream.close();
			    out.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		
		} else {  // 파일이 없을 때.
			logger.info("##### file not exists...");
			try {
				
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script type='text/javascript'>alert('file does not exist.'); history.go(-1);</script>");
				out.flush();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		logger.info("async task end");

	}
	

	private String getBrowser(HttpServletRequest request) {
		if (request == null) {
			return "Chrome";
		}
		String header = request.getHeader("User-Agent");
		if (header == null) {
			return "Chrome";
		}
		if (header.indexOf("MSIE") > -1) {
			return "MSIE";
		} else if (header.indexOf("Chrome") > -1) {
			return "Chrome";
		} else if (header.indexOf("Opera") > -1) {
			return "Opera";
		} else if (header.indexOf("Firefox") > -1) {
			return "Firefox";
		} else {
			return "";
		}
	}

	private String getDisposition(String filename, String browser) {
		
		String dispositionPrefix = "attachment; filename=";
		
		String encodedFilename = null;

		try {
			if (browser.equals("MSIE")) {
				encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
			
			} else if (browser.equals("Firefox")) {
				encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
			
			} else if (browser.equals("Opera")) {
				encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
			
			} else if (browser.equals("Chrome")) {
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < filename.length(); i++) {
					char c = filename.charAt(i);
					if (c > '~') {
						sb.append(URLEncoder.encode("" + c, "UTF-8"));
					} else {
						sb.append(c);
					}
				}
				encodedFilename = sb.toString();
			} else {
				//throw new RuntimeException("Not supported browser");
				encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return dispositionPrefix + encodedFilename + ";";
	}
	
}
