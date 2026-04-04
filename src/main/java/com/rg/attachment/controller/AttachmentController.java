package com.rg.attachment.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.core.io.UrlResource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import org.springframework.web.util.UriUtils;

import com.rg.attachment.dto.AttachmentDTO;
import com.rg.attachment.service.AttachmentService;
import com.rg.login.dto.UserDetailsVO;
import com.rg.util.RedisService3;

@Controller
public class AttachmentController {

	private final Logger logger = LogManager.getLogger(AttachmentController.class);

	@Autowired
	private AttachmentService attachmentService;
	
	@Autowired
	private RedisService3 redisService;
	
	@RequestMapping(value= {"/rg/getAttachmentList.do", "/getAttachmentList.do"})
	@ResponseBody
	public List<AttachmentDTO> getAttachmentList(AttachmentDTO attachmentDTO, HttpServletRequest request) {
		//logger.debug("#################### getAttachmentList RequestURL : " + request.getRequestURL().toString());
		if (request.getRequestURL().toString().endsWith("jisblee.me/getAttachmentList.do")
			|| request.getRequestURL().toString().endsWith("jisblee.me:4000/getAttachmentList.do")) {
			attachmentDTO.setOpenYn("Y");
		}
		
		attachmentDTO.setRequestURI(request.getRequestURI());
		
		
		if (attachmentDTO.getRequestURI().startsWith("/rg")) {

			HttpSession session = request.getSession();
			
			String loginId = (String)session.getAttribute("loginId");
			
			if (loginId == null || "".equals(loginId)) {
				
				return new ArrayList<AttachmentDTO>();
			
			} else {
				
				UserDetailsVO vo = redisService.selectRedisSession("LOGIN||SESSION||" + loginId + "||" + session.getId());
				
				if (vo == null || vo.getLoginId() == null || "".equals(vo.getLoginId()) || "null".equals(vo.getLoginId())) {
					return new ArrayList<AttachmentDTO>();
				}
			}
			
		}
		
		return attachmentService.getAttachmentList(attachmentDTO);
	}

	//@RequestMapping("/fileUpload.do")
	//@ResponseBody
	//public String fileUpload(AttachmentDTO attachmentDTO) {
		//return attachmentService.getAttachmentListTotalCount(attachmentDTO);
		//return "fileUpload";
	//}
	
	@RequestMapping("/rg/getAttachmentListTotalCount.do")
	@ResponseBody
	public Integer getAttachmentListTotalCount(AttachmentDTO attachmentDTO) {
		return attachmentService.getAttachmentListTotalCount(attachmentDTO);
	}

	@RequestMapping("/rg/checkAttachmentExists.do")
	@ResponseBody
	public void checkAttachmentExists() {
		attachmentService.checkAttachmentExists();
	}

	@RequestMapping("/rg/updateFileMapping.do")
	@ResponseBody
	public void updateFileMapping(AttachmentDTO attachmentDTO) {
		attachmentService.updateFileMapping(attachmentDTO);
	}

	@PostMapping("/rg/insertAttachment.do")
	@ResponseBody
	public AttachmentDTO insertAttachment(AttachmentDTO attachmentDTO, HttpServletRequest request) {
		//logger.debug("##############################################################");
		return attachmentService.insertAttachment(attachmentDTO, request);
	}

	@RequestMapping("/rg/deleteAttachment.do")
	@ResponseBody
	public int deleteAttachment(AttachmentDTO attachmentDTO, HttpServletRequest request) {

		HttpSession session = request.getSession();
		String userIdModified = (String) session.getAttribute("loginId");
		attachmentDTO.setUserIdModified(userIdModified);

		//logger.debug("###########################################################");
		//logger.debug("attachmentDTO.getAttachmentIdx() : " + attachmentDTO.getAttachmentIdx());

		//Enumeration<String> e = request.getParameterNames();
		//while (e.hasMoreElements()) {
			//String s = e.nextElement();
			//logger.debug("e.nextElement() : " + s + " : " + request.getParameter(s));
		//}
		//logger.debug("###########################################################");

		return attachmentService.deleteAttachment(attachmentDTO);
	}

	/*
	 * @RequestMapping("/getFileUploadPath.do")
	 * 
	 * @ResponseBody public String getFileUploadPath() { return
	 * AttachmentService.getFileUploadPath(); }
	 */

	@RequestMapping(value = "/fileDownloadMultiple.do")
	@ResponseBody
	public Map<String, List<String>> downloadFileMultiple(HttpServletRequest request, HttpServletResponse response) {

		/*
        try {
            ScriptEngineManager mgr = new ScriptEngineManager();
            ScriptEngine engine = mgr.getEngineByName("JavaScript");
            //engine.eval(showdownJs);
            //engine.eval("var markdown = '" + markdown + "';");
            //engine.eval("var converter = new Showdown.converter();");
            //engine.eval("var html = converter.makeHtml(markdown);");
            engine.eval("alert('1');");
            engine.
            return (String) engine.get("html");
        } catch (ScriptException e) {
            // Shouldn't happen unless somebody breaks the script
            throw new RuntimeException(e);
        }
        */

		/*
		try {
			response.setHeader("Content-Type", "application/javascript");

			PrintWriter writer = response.getWriter();
			
			//writer.println("<script type='text/javascript'>");
			writer.println("alert('1');");
			//writer.println("</script>");
			
			writer.flush();
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/

		//response.setHeader("Set-Cookie", "fileDownload=true; path=/");
		
		//ModelAndView mav = new ModelAndView();
		
		List<String> serverFileList = new ArrayList<String>();
		List<String> downFileList = new ArrayList<String>();

		//serverFileList.add("20180504230748-035dc6f8-cea2-454b-87fc-3762cded361a.exe");
		//serverFileList.add("20180504230807-86d63c88-01f6-4f26-8c75-d0c17fceb5ff.exe");

		serverFileList.add("275");
		serverFileList.add("273");
		serverFileList.add("274");

		//downFileList.add("7z1805.exe");
		//downFileList.add("7z1805-x64.exe");
		
		downFileList.add("20180512163409-fce8d9c5-c5ea-4d64-97f3-449e13256b09");
		downFileList.add("20180504230748-035dc6f8-cea2-454b-87fc-3762cded361a");
		downFileList.add("20180504230807-86d63c88-01f6-4f26-8c75-d0c17fceb5ff");
		
		HttpSession session = request.getSession();
		session.setAttribute("20180512163409-fce8d9c5-c5ea-4d64-97f3-449e13256b09", "true");
		session.setAttribute("20180504230748-035dc6f8-cea2-454b-87fc-3762cded361a", "true");
		session.setAttribute("20180504230807-86d63c88-01f6-4f26-8c75-d0c17fceb5ff", "true");

		//Cookie cookie = null;
		
		//cookie = new Cookie("20180512163409-fce8d9c5-c5ea-4d64-97f3-449e13256b09", "true");
		//cookie.setPath("/");
		//cookie.setMaxAge(0);
		//cookie.setSecure(true);
		//response.addCookie(cookie);
		
		//cookie = new Cookie("20180504230748-035dc6f8-cea2-454b-87fc-3762cded361a", "true");
		//cookie.setPath("/");
		//cookie.setMaxAge(0);
		//cookie.setSecure(true);
		//response.addCookie(cookie);
		
		//cookie = new Cookie("20180504230807-86d63c88-01f6-4f26-8c75-d0c17fceb5ff", "true");
		//cookie.setPath("/");
		//cookie.setMaxAge(0);
		//cookie.setSecure(true);
		//response.addCookie(cookie);
		
		
		//mav.addObject("serverFileList", serverFileList);
		//mav.addObject("downFileList", downFileList);
		
		//mav.setViewName("rg/fileDownloadMultiple");
	
		//return mav;
		
		
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		
		map.put("idxs", serverFileList);
		map.put("filenames", downFileList);
		
		return map;
		
		/*
		try {
			
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('로그인 정보를 확인해주세요.');</script>");
			out.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
	}

	@RequestMapping(value = "/getSession.do")
	@ResponseBody
	public Map<String, String> getSession(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> map = new HashMap<String, String>();
		HttpSession session = request.getSession();
		map.put("session", (String)session.getAttribute(request.getParameter("sessionName") == null ? "" : request.getParameter("sessionName")));
		return map;
	}

	

	@RequestMapping(value = {"/fileDownload.do", "/rg/fileDownload.do"})
	public ResponseEntity<StreamingResponseBody> downloadFile(HttpServletRequest request, HttpServletResponse response) {

		String idx = request.getParameter("idx");
		String filename = request.getParameter("filename");

		if (idx == null || "".equals(idx) || filename == null || "".equals(filename)) {
			//return;
			//throw new RuntimeException("the given URL path is not valid");
			return ResponseEntity
		            .status(HttpStatus.BAD_REQUEST)
		            .body(null);
		}

		String fileUploadPath = AttachmentService.getFileUploadPath("0");

		AttachmentDTO attachmentDTO = attachmentService.getAttachmentDTOInfo(idx);

		if (attachmentDTO == null || !filename.equals(attachmentDTO.getServerFileName())) {
			//logger.error("############################");
			//logger.error(filename + " : " + attachmentDTO.getServerFileName());
			//return;
			//throw new RuntimeException("the given URL path is not valid");
			return ResponseEntity
		            .status(HttpStatus.BAD_REQUEST)
		            .body(null);
		}
		
		
		String filePath = fileUploadPath + filename .substring(0, 4) + "/" + filename + "." + attachmentDTO.getAttachmentExt();
		File f = new File(filePath);
		
		StreamingResponseBody stream = null;
		
		if (f.exists()) {
			//UrlResource resource;
		    //try{
		        //resource = new UrlResource("file:"+ filePath);
		    //}catch (MalformedURLException e){
		        //logger.error("the given File path is not valid");
		        //e.getStackTrace();
		        //throw new RuntimeException("the given URL path is not valid");
		    //}
			
			try {
			
				final java.io.InputStream inputStream = new FileInputStream(f);

			    stream = outputStream -> {
			        byte[] buffer = new byte[1024];
			        int bytesRead;
			        while ((bytesRead = inputStream.read(buffer)) != -1) {
			            outputStream.write(buffer, 0, bytesRead);
			        }
			        inputStream.close();
			    };
			    
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		    //Header
		    String originalFileName = attachmentDTO.getAttachmentName() + "." + attachmentDTO.getAttachmentExt();
		    String encodedOriginalFileName = UriUtils.encode(originalFileName, StandardCharsets.UTF_8);

		    String contentDisposition = "attachment; filename=\"" + encodedOriginalFileName + "\"";

			HttpSession session = request.getSession();
			session.removeAttribute(request.getParameter("filename"));
			
			attachmentService.saveDownloadHistory(request, attachmentDTO);
			
		    return ResponseEntity
		            .ok()
		            .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
		            .contentType(MediaType.APPLICATION_OCTET_STREAM)
		            .contentLength(f.length())
		            .body(stream);
		
		} else {  // 파일이 없을 때.
			//throw new RuntimeException("the given file does not exists.");
			return ResponseEntity
		            .status(HttpStatus.BAD_REQUEST)
		            .body(null);
		}
	}

	
	
	@RequestMapping(value = {"/fileDownload_backup_20250608.do", "/rg/fileDownload_backup_20250608.do"})
	public ResponseEntity<Object> downloadFile_backup_20250608(HttpServletRequest request, HttpServletResponse response) {

		String idx = request.getParameter("idx");
		String filename = request.getParameter("filename");

		if (idx == null || "".equals(idx) || filename == null || "".equals(filename)) {
			//return;
			//throw new RuntimeException("the given URL path is not valid");
			return ResponseEntity
		            .status(HttpStatus.BAD_REQUEST)
		            .body("the given URL path is not valid");
		}

		String fileUploadPath = AttachmentService.getFileUploadPath("0");

		AttachmentDTO attachmentDTO = attachmentService.getAttachmentDTOInfo(idx);

		if (attachmentDTO == null || !filename.equals(attachmentDTO.getServerFileName())) {
			//logger.error("############################");
			//logger.error(filename + " : " + attachmentDTO.getServerFileName());
			//return;
			//throw new RuntimeException("the given URL path is not valid");
			return ResponseEntity
		            .status(HttpStatus.BAD_REQUEST)
		            .body("the given URL path is not valid");
		}
		String filePath = fileUploadPath + filename .substring(0, 4) + "/" + filename + "." + attachmentDTO.getAttachmentExt();
		File f = new File(filePath);

		if (f.exists()) {
			UrlResource resource;
		    try{
		        resource = new UrlResource("file:"+ filePath);
		    }catch (MalformedURLException e){
		        logger.error("the given File path is not valid");
		        e.getStackTrace();
		        throw new RuntimeException("the given URL path is not valid");
		    }
		    //Header
		    String originalFileName = attachmentDTO.getAttachmentName() + "." + attachmentDTO.getAttachmentExt();
		    String encodedOriginalFileName = UriUtils.encode(originalFileName, StandardCharsets.UTF_8);

		    String contentDisposition = "attachment; filename=\"" + encodedOriginalFileName + "\"";

			HttpSession session = request.getSession();
			session.removeAttribute(request.getParameter("filename"));
			
			attachmentService.saveDownloadHistory(request, attachmentDTO);
			
		    return ResponseEntity
		            .ok()
		            .header(HttpHeaders.CONTENT_DISPOSITION,contentDisposition)
		            .body(resource);
		
		} else {  // 파일이 없을 때.
			//throw new RuntimeException("the given file does not exists.");
			return ResponseEntity
		            .status(HttpStatus.BAD_REQUEST)
		            .body("the given file does not exists.");
		}
	}

	
	@RequestMapping(value = {"/fileDownload_backup_20240427.do", "/rg/fileDownload_backup_20240427.do"})
	public void downloadFile_backup_20240427(HttpServletRequest request, HttpServletResponse response) {

		String idx = request.getParameter("idx");
		String filename = request.getParameter("filename");

		if (idx == null || "".equals(idx) || filename == null || "".equals(filename)) {
			return;
		}

		String fileUploadPath = AttachmentService.getFileUploadPath("0");

		AttachmentDTO attachmentDTO = attachmentService.getAttachmentDTOInfo(idx);

		if (!filename.equals(attachmentDTO.getServerFileName())) {
			//logger.error("############################");
			//logger.error(filename + " : " + attachmentDTO.getServerFileName());
			return;
		}

		File f = new File(fileUploadPath + filename .substring(0, 4) + "/" + filename + "." + attachmentDTO.getAttachmentExt());

		if (f.exists()) {
			try {

				filename = attachmentDTO.getAttachmentName() + "." + attachmentDTO.getAttachmentExt();
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
			    FileInputStream fileInputStream = new FileInputStream(f);
			    
			    FileCopyUtils.copy(fileInputStream, out);
			    
			    //byte[] buf = new byte[8192];
			    //int bytesread = 0, bytesBuffered = 0;
			    //while( (bytesread = fileInputStream.read( buf )) > -1 ) {
			        //out.write( buf, 0, bytesread );
			        //bytesBuffered += bytesread;
			        //if (bytesBuffered > 1024 * 1024) { //flush after 1MB
			            //bytesBuffered = 0;
			            //out.flush();
			        //}
			    //}
			    fileInputStream.close();
			    out.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		
		} else {  // 파일이 없을 때.
			try {
				
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script type='text/javascript'>alert('file does not exist.'); history.go(-1);</script>");
				out.flush();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		HttpSession session = request.getSession();
		session.removeAttribute(request.getParameter("filename"));
		
		//logger.debug("#################### cookie delete : " + request.getParameter("filename"));
		
		//Cookie cookie = new Cookie(request.getParameter("filename"), "false");
		//cookie.setPath("/");
		//cookie.setMaxAge(0);
		//cookie.setSecure(true);
		//response.addCookie(cookie);
		
		
		//Cookie[] cookies = request.getCookies();
		//for (int i = 0; i < cookies.length; i++) {
		    //cookies[i].getName(); 
		    //cookies[i].getValue();
		    //if (cookies[i].getName().equals(request.getParameter("filename"))) {
		    	//cookies[i].setValue("false");
			    //cookies[i].setMaxAge(0);                 //쿠키 유지기간을 0으로함
			    //cookies[i].setPath("/");                    //쿠키 접근 경로 지정
			    //cookies[i].setSecure(true);
			    //cookies[i].setDomain("jisblee.me");
			    //response.addCookie(cookies[i]);      //쿠키저장
		    //}
		//}
		

		//response.addCookie(new Cookie(request.getParameter("filename"), "false"));
		
		//System.out.println();
		//cookies = request.getCookies();
		//for (int i = 0; i < cookies.length; i++) {
		    //System.out.println(cookies[i].getName() + " : " + cookies[i].getValue());
		//}
		//System.out.println();
		
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
	
	//@Autowired
	//AsyncService asyncService;
	
	@RequestMapping(value = {"/fileDownloadSimultaneously.do"})
	public void fileDownload(String idx, String filename, HttpServletRequest request, HttpServletResponse response) {
		/*
		AttachmentDTO attachmentDTO = attachmentService.getAttachmentDTOInfo("506");
	    CompletableFuture page1 = fileDownload1("506", "20201107195000-8344df6a-a089-4c94-a4f1-aeabad760741", attachmentDTO,
				request, response);
	    attachmentDTO = attachmentService.getAttachmentDTOInfo("505");
		CompletableFuture page2 = fileDownload1("505", "20201107114712-3fffcf4d-2c4b-4501-9572-a2b251767c6e", attachmentDTO,
	    		request, response);

	    CompletableFuture.allOf(page1,page2).join();
	    */
		
		//System.setProperty("org.apache.catalina.connector.RECYCLE_FACADES", "true");


		//출처: https://seongtak-yoon.tistory.com/76 [테이키스토리]
		
		/*
		AttachmentDTO attachmentDTO = null;
		attachmentDTO = attachmentService.getAttachmentDTOInfo("506");
		asyncService.onAsyncTask(attachmentDTO, request, response);
		attachmentDTO = attachmentService.getAttachmentDTOInfo("505");
		asyncService.onAsyncTask(attachmentDTO, request, response);
		*/
		
		
		List<AttachmentDTO> attachments = new ArrayList<AttachmentDTO>();
		AttachmentDTO attachmentDTO = null;
		attachmentDTO = attachmentService.getAttachmentDTOInfo("506");
		attachments.add(attachmentDTO);
		attachmentDTO = attachmentService.getAttachmentDTOInfo("505");
		attachments.add(attachmentDTO);
		
		ExecutorService executorService = Executors.newCachedThreadPool();
		for (final AttachmentDTO attachment : attachments) {
			executorService.submit(new Runnable() {

				@Override
				public void run() {
					try{
						fileDownload1(attachment, request, response);
						//String attUrl = attachment.getUrl();
						//String fileName = attachment.getFileName();
						//String destLocation = "C:\Users\attachments";
						//URL url = new URL(attUrl);
						//String fileLocation = new File(destLoc, fileName);
						//FileUtils.copyURLToFile(url, fileLocation);
						//if(fileLocation.exists()) {
						//attachment.setDownStatus("Completed");
						//}
					} catch(Exception e){
						//attachment.setDownStatus("Failed");
						e.printStackTrace();
					}
				}
			});
		}
		executorService.shutdown();
		
		
		
		/*
		ExecutorService pool = Executors.newFixedThreadPool(2);
		for (int i=0; i < 2; i++) {
			logger.info("##### i : " + i);
			
			
			//https://jisblee.me/fileDownload.do?idx=505&filename=20201107114712-3fffcf4d-2c4b-4501-9572-a2b251767c6e
			
			if (i == 0) {
				AttachmentDTO attachmentDTO = attachmentService.getAttachmentDTOInfo("506");
				pool.submit(new DownloadTask(attachmentDTO.getAttachmentIdx(), attachmentDTO.getServerFileName(), attachmentDTO,
						request, response));
			} else if (i == 1) {
				AttachmentDTO attachmentDTO = attachmentService.getAttachmentDTOInfo("505");
				pool.submit(new DownloadTask(attachmentDTO.getAttachmentIdx(), attachmentDTO.getServerFileName(), attachmentDTO,
			    		request, response));
			}
		}
		pool.shutdown();
		try {
			pool.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		*/
	}
	
	/*
	private static class DownloadTask implements Runnable {

	    private String idx;
	    private final String filename;
	    HttpServletRequest request; 
	    HttpServletResponse response;
	    AttachmentDTO attachmentDTO;
	    
	    public DownloadTask(String idx, String filename, AttachmentDTO attachmentDTO1, 
	    		HttpServletRequest request1, HttpServletResponse response1) {
	        this.idx = idx;
	        this.filename = filename;
	        this.request = request1;
	        this.response = response1;
	        this.attachmentDTO = attachmentDTO1;
	    }

	    @Override
	    public void run() {
	    	System.out.println("##### idx : " + idx);
	        // surround with try-catch if downloadFile() throws something
	    	AttachmentController attachmentController = new AttachmentController();
	    	//attachmentController.fileDownload1(idx, filename, attachmentDTO, request, response);
	    	
	    	
	    	String htmlUrl = "http://jisblee.me/fileDownload1.do?idx=506&filename=20201107195000-8344df6a-a089-4c94-a4f1-aeabad760741";

	    	HttpURLConnection conn = null;
	    	BufferedReader in = null;
			try {
				
		    	conn = (HttpURLConnection) new URL(htmlUrl).openConnection();
		    	conn.setRequestMethod("GET");
		    	conn.setRequestProperty("User-Agent", "Mozilla/5.0");
	
		    	StringBuffer sb = new StringBuffer();
		    	in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		    	String inputLine;
	
		    	while ((inputLine = in.readLine()) != null) {
		    		sb.append(inputLine);
		    	}


			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
	    	//출처: https://kamsi76.tistory.com/entry/JAVA-URLConnection-HTTPS-처리 [Kamsi's Blog]
	    }
	}
	*/
	
	//@RequestMapping(value = {"/fileDownload1.do"})
	@Async
	//public CompletableFuture fileDownload1(String idx, String filename, AttachmentDTO attachmentDTO,
	public void fileDownload1(AttachmentDTO attachmentDTO, 
			HttpServletRequest request, HttpServletResponse response) {

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
        
		
		response.setHeader("Content-Disposition", "attachment; filename=\"" + attachmentDTO.getAttachmentName() + "." + attachmentDTO.getAttachmentExt() + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary");
        
        OutputStream out = null;
		try {
			out = response.getOutputStream();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
        FileInputStream fis = null;
        
        //파일 카피 후 마무리
        try {
            fis = new FileInputStream(f);
            if (fis != null && out != null) {
            	FileCopyUtils.copy(fis, out);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (Exception e) {}
            }
        }
        //출처: https://jdkblog.tistory.com/133 [JDK's blog]
        
        
		/*
		HttpServletResponseCopier response = null;
		try {
			response = new HttpServletResponseCopier((HttpServletResponse) response1);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

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
		*/
		
		//HttpSession session = request.getSession();
		//session.removeAttribute(request.getParameter("filename"));
		
		//return CompletableFuture.completedFuture(null);
	}

}
