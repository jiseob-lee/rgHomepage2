package com.rg.util.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RestTestController {

	@GetMapping("/api/test")
	@ResponseBody
	public String apiTest() {
		return "/api/test";
	}

	@GetMapping("/api/test.do")
	@ResponseBody
	public String apiTestDo() {
		return "/api/test.do";
	}

	@GetMapping("/test.do")
	@ResponseBody
	public String testDo() {
		return "/test.do";
	}
}
