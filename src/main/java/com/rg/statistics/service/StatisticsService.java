package com.rg.statistics.service;

import java.util.List;
import java.util.Map;

import com.rg.statistics.dto.ArticleCountDTO;
import com.rg.statistics.dto.LogDTO;

public interface StatisticsService {

	public Map<String, Object> getIpCount(int pageNo);
	
	public List<ArticleCountDTO> getStatisticsArticleSubjects(Map<String, String> map);

	public List<LogDTO> getStatisticsLog(String date);
	
}
