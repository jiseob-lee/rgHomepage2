package com.rg.customer.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rg.attachment.dto.AttachmentDTO;
import com.rg.attachment.service.AttachmentService;
import com.rg.customer.dto.CustomerDTO;
import com.rg.customer.service.CustomerService;

@Controller
public class CustomerController {

	private final Logger logger = LogManager.getLogger(CustomerController.class);
	
	@Autowired
	private CustomerService customerService;

	@Autowired
	private AttachmentService attachmentService;

	
	@RequestMapping("/getCustomerList.do")
	@ResponseBody
	public List<CustomerDTO> getCustomerList(CustomerDTO customerDTO) {
		return customerService.getCustomerList(customerDTO);
	}
	
	@PostMapping("/staff/insertCustomer.do")
	@ResponseBody
	public int insertBoardArticle(
			AttachmentDTO attachmentDTO,
			@RequestParam(value = "customerIdx", required = false) String[] customerIdx,
			@RequestParam(value = "customerName", required = false) String[] customerName,
			@RequestParam(value = "orderNo", required = false) String[] orderNo,
			@RequestParam(value = "url", required = false) String[] url,
			HttpServletRequest request) {
		
		int result = 0;

		List<CustomerDTO> list = new ArrayList<CustomerDTO>();
		
		int insertedCustomerIdx = 0;

		// 새로운 것 등록
		if (request.getParameter("customerNameNew") != null) {
			logger.debug("#################### 5 #################");
			attachmentDTO = attachmentService.insertAttachment(attachmentDTO, request);

			logger.debug("################### AttachmentName : " + attachmentDTO.getAttachmentName());

			CustomerDTO customerDTO = new CustomerDTO();
			customerDTO.setCustomerName(request.getParameter("customerNameNew"));
			customerDTO.setOrderNo(request.getParameter("orderNoNew"));
			customerDTO.setUrl(request.getParameter("urlNew"));
			
			if (attachmentDTO != null) {
				logger.debug("#################### 6 #################");
				try {
					String filename = AttachmentService.getFileUploadPath(attachmentDTO.getBoardIdx()) + attachmentDTO.getServerFileName() + "." + attachmentDTO.getAttachmentExt();
					BufferedImage bimg = ImageIO.read(new File(filename));
					customerDTO.setImageFile(attachmentDTO.getServerFileName() + "." + attachmentDTO.getAttachmentExt());
					customerDTO.setImageWidth(bimg.getWidth());
					customerDTO.setImageHeight(bimg.getHeight());
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				customerDTO = customerService.insertCustomer(customerDTO);
				
				if (customerDTO != null) {
					insertedCustomerIdx = customerDTO.getCustomerIdx();
					result++;
				}
			}
		}
		
		logger.debug("#################### 1 #################");

		// 기존 것 업데이트
		/*
		if (request.getParameter("customerIdx") == null) {  // 처음으로 이미지 파일과 함께 하나만 입력될 때, 위에서 먼저 처리되었음.
			logger.debug("#################### 2 #################");
			;
			
		} else
		*/
		if (customerIdx != null) {  // 기존에 입력된 것이 여러개 일 때
			logger.debug("#################### 3 #################");
			for (int i=0; i<customerIdx.length; i++) {
				CustomerDTO customerDTO = new CustomerDTO();
				customerDTO.setCustomerIdx(Integer.parseInt(customerIdx[i]));
				customerDTO.setCustomerName(customerName[i]);
				customerDTO.setOrderNo(orderNo[i]);
				customerDTO.setUrl(url[i]);
				
				list.add(customerDTO);
			}
		
		} else if (request.getParameter("customerIdx") != null) {  // 기본에 입력된 것이 한 개 일때
			logger.debug("#################### 4 #################");
			CustomerDTO customerDTO = new CustomerDTO();
			customerDTO.setCustomerName(request.getParameter("customerName"));
			customerDTO.setOrderNo(request.getParameter("orderNo"));
			customerDTO.setUrl(request.getParameter("url"));
			customerDTO.setCustomerIdx(Integer.parseInt(request.getParameter("customerIdx")));
			
			list.add(customerDTO);
		}

		result += customerService.updateCustomer(list, insertedCustomerIdx);
		
		return result;
	}

}
