package com.rg.attachment.service;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.AddressNotFoundException;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.City;
import com.maxmind.geoip2.record.Country;
import com.maxmind.geoip2.record.Subdivision;
import com.rg.attachment.dao.AttachmentDAOImpl;
import com.rg.attachment.dto.AttachmentDTO;
import com.rg.board.dao.BoardDAOImpl;
import com.rg.board.dto.BoardDTO;
import com.rg.util.GeoLite2;
import com.rg.util.IP;

import jakarta.servlet.http.HttpServletRequest;

@Service("attachmentService")
public class AttachmentServiceImpl implements AttachmentService {

	private final Logger logger = LogManager.getLogger(AttachmentServiceImpl.class);
	
	private static String fileUploadPath = AttachmentService.getFileUploadPath("1");
	private static String customerUploadPath = AttachmentService.getFileUploadPath("4");
	private static String imageUploadPath = AttachmentService.getFileUploadPath("img");

	@Autowired
	private AttachmentDAOImpl attachmentDAO;

	@Autowired
	private BoardDAOImpl boardDAO;
	
	@Override
	public List<AttachmentDTO> getAttachmentList(AttachmentDTO attachmentDTO) {
		

		String requestURI = attachmentDTO.getRequestURI();
		
		String boardArticleIdx = attachmentDTO.getBoardArticleIdx();
		
		BoardDTO boardDTO = new BoardDTO();
		
		if (boardArticleIdx == null || "".equals(boardArticleIdx) || hasNonDigit(boardArticleIdx)) {
			boardDTO.setBoardArticleIdx(0);
		} else {
			boardDTO.setBoardArticleIdx(Integer.parseInt(boardArticleIdx));
		}
		
		BoardDTO returnDTO = boardDAO.getBoardContent(boardDTO);
		
		String openYn = returnDTO == null ? "n" : returnDTO.getOpenYn();
		
		if (requestURI != null && !requestURI.startsWith("/rg") && openYn != null && !openYn.equalsIgnoreCase("Y")) {
			return new ArrayList<AttachmentDTO>();
		}
		
		return attachmentDAO.getAttachmentList(attachmentDTO);
	}
	
	@Override
	public void updateFileMapping(AttachmentDTO attachmentDTO) {
		int attachmentOrder = attachmentDAO.getMaxAttachmentOrder(attachmentDTO);
		attachmentDTO.setAttachmentOrder(attachmentOrder);
		logger.debug("##################################### attachmentOrder : " + attachmentOrder + " ###############################");
		attachmentDAO.updateFileMapping(attachmentDTO);
	}
	
	@Override
	public AttachmentDTO insertAttachment(AttachmentDTO attachmentDTO, HttpServletRequest request) {

		//start
        long lStartTime = System.currentTimeMillis();
        
		String uploadedFileName = "" + attachmentDTO.getAttachmentExt();
		String uploadedFileExt = "";
		
		String serverFileName = getServerFileName();
		attachmentDTO.setServerFileName(serverFileName);
		
		logger.debug("#########################################################");
		logger.debug("serverFileName : " + serverFileName);
		logger.debug("fileUploadPath : " + fileUploadPath);
		
        try {
		    MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
		    Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
		    MultipartFile multipartFile = null;
		    
		    if (!iterator.hasNext()) {
		    	return null;
		    }
		    
		    while (iterator.hasNext()) {
		    	
		        multipartFile = multipartHttpServletRequest.getFile(iterator.next());
		        uploadedFileName = multipartFile.getOriginalFilename();
		        
		        if (uploadedFileName.lastIndexOf(".") == -1) {
		        	return null;
		        } else {
		        	uploadedFileExt = uploadedFileName.substring(uploadedFileName.lastIndexOf(".") + 1).toLowerCase();
		        	attachmentDTO.setAttachmentName(uploadedFileName.substring(0, uploadedFileName.lastIndexOf(".")));
		        	attachmentDTO.setAttachmentExt(uploadedFileExt);
		        	attachmentDTO.setAttachmentSize(multipartFile.getSize());
		        }
		        
		        if (multipartFile.isEmpty() == false) {
		        	logger.debug("------------- file start -------------");
		        	logger.debug("name : " + multipartFile.getName());
		        	logger.debug("filename : " + multipartFile.getOriginalFilename());
		        	logger.debug("size : " + multipartFile.getSize());
		        	logger.debug("ext : " + attachmentDTO.getAttachmentExt());
		        	logger.debug("-------------- file end --------------\n");
		        }
		        
		        String fileNew = null;
		        
		        if ("4".equals(attachmentDTO.getBoardIdx())) {
		        	fileNew = customerUploadPath + serverFileName + "." + uploadedFileExt;
		        } else if ("img".equals(attachmentDTO.getBoardIdx())) {
		        	File directory = new File(imageUploadPath + serverFileName.substring(0, 4));
		        	if (!directory.exists()) {
		        		directory.mkdir();
						try {
							Runtime.getRuntime()
							  .exec(String.format("chmod o+x %s", directory));
						} catch (IOException e) {
							e.printStackTrace();
						}
		        	}
		        	fileNew = imageUploadPath + serverFileName.substring(0, 4) + "/" + serverFileName + "." + uploadedFileExt;
		        } else {
		        	File directory = new File(fileUploadPath + serverFileName.substring(0, 4));
		        	if (!directory.exists()) {
		        		directory.mkdir();
		        	}
		        	fileNew = fileUploadPath + serverFileName.substring(0, 4) + "/" + serverFileName + "." + uploadedFileExt;
		        }
		        
		        multipartFile.transferTo(new File(fileNew));
		        
		        if (!fileUploadPath.startsWith("E:/")) {
					try {
						Runtime.getRuntime()
						  .exec(String.format("chmod o+r %s", fileNew));
					} catch (IOException e) {
						e.printStackTrace();
					}
		        }

				
				/*
				try {
			        Date date = new Date();
			        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			        int year = localDate.getYear();
			        int month = localDate.getMonthValue();
			        
			        String s3FileName = year + "/" + month + "/" + serverFileName + "." + uploadedFileExt;
			        
					S3HighLevelMultipartUpload.s3Upload(fileNew, s3FileName);
				} catch (Exception e) {
					e.printStackTrace();
				}
				*/
				
				
		    }
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

        if (!"img".equals(attachmentDTO.getBoardIdx())) {
        	attachmentDTO = attachmentDAO.insertAttachment(attachmentDTO);
        }
        
        logger.debug("########################################################");
        logger.debug("getAttachmentIdx : " + attachmentDTO.getAttachmentIdx());

		//end
        long lEndTime = System.currentTimeMillis();

		//time elapsed
        long output = lEndTime - lStartTime;

        System.out.println("Elapsed time in milliseconds: " + output);

		return attachmentDTO;
	}

	@Override
	public int deleteAttachment(AttachmentDTO attachmentDTO) {
		
		String fileName = AttachmentService.getFileUploadPath(attachmentDTO.getBoardIdx()) 
				+ attachmentDTO.getServerFileName().substring(0, 4) + "/" + attachmentDTO.getServerFileName();
		
		File f = new File(fileName);
		if (f.exists()) {
			f.delete();
		}
		
		return attachmentDAO.deleteAttachment(attachmentDTO);
	}

	private String getServerFileName() {
		UUID uuid = UUID.randomUUID();
		String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
		return timeStamp + "-" + uuid;
	}
	
	public AttachmentDTO getAttachmentDTOInfo(String attachmentIdx) {
		if (hasNonDigit(attachmentIdx)) {
			return new AttachmentDTO();
		}
		return attachmentDAO.getAttachmentDTOInfo(attachmentIdx);
	}
	
	public boolean hasNonDigit(String str) {
	    if (str == null || str.isEmpty()) {
	        return true; // 필요에 따라 true로 바꿀 수도 있음
	    }
	    return !str.matches("\\d+");
	}
	
	@Override
	public int getAttachmentListTotalCount(AttachmentDTO attachmentDTO) {
		return attachmentDAO.getAttachmentListTotalCount(attachmentDTO);
	}
	
	@Override
	public void checkAttachmentExists() {
		
		List<String> list0 = new ArrayList<String>();  // 파일이 존재하지 않음.
		List<String> list1 = new ArrayList<String>();  // 파일이 존재함.
		
		AttachmentDTO attachmentDTO = new AttachmentDTO();
		attachmentDTO.setListLimit(Integer.valueOf(getAttachmentListTotalCount(attachmentDTO)).toString());
		attachmentDTO.setListOffset("0");
		
		List<AttachmentDTO> list = getAttachmentList(attachmentDTO);
		
		for (int i=0; i<list.size(); i++) {
			
			AttachmentDTO a = list.get(i);
			
			File f = new File(fileUploadPath + a.getServerFileName().substring(0, 4) + "/" + a.getServerFileName() + "." + a.getAttachmentExt());
			
			if (f.exists()) {
				list1.add(a.getAttachmentIdx());  // 파일이 존재함.
			} else {
				list0.add(a.getAttachmentIdx());  // 파일이 존재하지 않음.
			}
		}
		
		list0.add("-1");
		
		//for ( Map.Entry<String, Integer> elem : map.entrySet() ){
			//System.out.println( String.format("키 : %s, 값 : %s", elem.getKey(), elem.getValue()) );
		//}
		//출처: http://stove99.tistory.com/96 [스토브 훌로구]
		
		logger.debug("###################### Existing : " + list1.size());
		logger.debug("###################### Non Existing : " + list0.size());
		
		attachmentDAO.updateFileExists(list1);
		attachmentDAO.updateFileNotExists(list0);
	}
	
	@Override
	public void saveDownloadHistory(HttpServletRequest request, AttachmentDTO attachmentDTO) {

		try {
			
			String ip = IP.getClientIP(request);
			
			attachmentDTO.setUserIp(ip);
			
			String GeoLite2Path = "D:/GeoLite2/GeoLite2-City.mmdb";
			
			// A File object pointing to your GeoIP2 or GeoLite2 database
			if (new File("/home/ubuntu/GeoLite2").exists()) {
				GeoLite2Path = "/home/ubuntu/GeoLite2/GeoLite2-City.mmdb";
			}
			
			File database = new File(GeoLite2Path);
			
			DatabaseReader reader = new DatabaseReader.Builder(database).build();
			
			InetAddress ipAddress = InetAddress.getByName(ip);
			
			CityResponse response = "127.0.0.1".equals(ip) ? null : reader.city(ipAddress);
	
			Country country = response == null ? null : response.getCountry();
			
			logger.debug(country == null ? "" : country.getIsoCode());
			logger.debug(country == null ? "" : country.getName());
			
			//indexDTO.setCountry(country.getName());
			
			Subdivision subdivision = response == null ? null : response.getMostSpecificSubdivision();
			//System.out.println(subdivision.getName()); 
			//indexDTO.setSubdivision(subdivision.getName());
			
			City city = response == null ? null : response.getCity();
			//indexDTO.setCity(city.getName());
			
			
			attachmentDTO.setUserCountry(country == null ? "" : country.getName());
			attachmentDTO.setUserSubdivision(subdivision == null ? "" : subdivision.getName());
			attachmentDTO.setUserCity(city == null ? "" : city.getName());
			
			attachmentDAO.saveDownloadHistory(attachmentDTO);
		
		} catch (AddressNotFoundException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		} catch (GeoIp2Exception e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		
	}
}

