package com.rg.statistics.dao;

import java.util.List;
import java.util.Map;

import com.rg.statistics.dto.ArticleCountDTO;
import com.rg.statistics.dto.IpCountDTO;
import com.rg.statistics.dto.LogDTO;

public interface StatisticsDAO {

	public List<IpCountDTO> getIpCount();
	
	public List<ArticleCountDTO> getStatisticsArticleSubjects(Map<String, String> map);
	
	public List<LogDTO> getStatisticsLog(String date);
	
}
