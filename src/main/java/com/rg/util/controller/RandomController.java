package com.rg.util.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Controller
public class RandomController {

	@GetMapping("/randomInt")
	@ResponseBody
	public Map<String, Integer> randomInt() {
		
		Map<String, Integer> map = new HashMap<>();
		
		Random rand = new Random();
		
		for (int i=0; i < 10; i++) {

			int num = rand.nextInt(20);
			
			map.put(String.valueOf(i), num);
		}
		
		
		return map;
	}
}
