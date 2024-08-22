package com.rg.sample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SampleController {

	@RequestMapping("/rg/sampleMapping.do")
	@ResponseBody
	public String sampleMapping() {
		return "{\"default\" : \"https://jisblee.me/assets/201806CheongSanDo/DSC00059.jpg\", \"900\" : \"https://jisblee.me/assets/201806CheongSanDo/DSC00059.jpg/w_900\", \"url\" : \"https://jisblee.me/assets/201806CheongSanDo/DSC00059.jpg\"}";
	}
	
	@RequestMapping("/rg/jstree.do")
	@ResponseBody
	public String jstree() {
		return "{\r\n"
				+ "								\"id\" : \"1\",\r\n"
				+ "								\"text\" : \"Root node\",\r\n"
				+ "								\"state\" : { \"opened\" : true },\r\n"
				+ "								\"children\" : [\r\n"
				+ "									{ \"id\" : \"c1\", \"text\" : \"Child node 1\" },\r\n"
				+ "									{ \"id\" : \"c2\", \"text\" : \"Child node 2\" },\r\n"
				+ "									{ \"id\" : \"c3\", \"text\" : \"Child node 3\" },\r\n"
				+ "									{ \"id\" : \"c4\", \"text\" : \"Child node 4\" },\r\n"
				+ "									{ \"id\" : \"c5\", \"text\" : \"Child node 5\" }\r\n"
				+ "								]\r\n"
				+ "							}";
	}
}
