package com.rg.statistics.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rg.statistics.dao.StatisticsDAOImpl;
import com.rg.statistics.dto.ArticleCountDTO;
import com.rg.statistics.dto.IpCountDTO;
import com.rg.statistics.dto.LogDTO;

@Service("statisticsService")
public class StatisticsServiceImpl implements StatisticsService {

	private final Logger logger = LogManager.getLogger(StatisticsServiceImpl.class);
	
	@Autowired
	private StatisticsDAOImpl statisticsDAO;

	@Override
	public Map<String, Object> getIpCount(int pageNo) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int listLimit = 20;
		map.put("listLimit", listLimit);
		
		List<IpCountDTO> list = statisticsDAO.getIpCount();
		
		map.put("totalCount", list.size());
		map.put("pageNo", pageNo);
		
		List<IpCountDTO> list2 = list.stream()
									.skip((pageNo - 1) * listLimit)
									.limit(listLimit)
									.collect(Collectors.toList());
		
		map.put("list", list2);
		
		return map;
	}
	
	public List<ArticleCountDTO> getStatisticsArticleSubjects(Map<String, String> map) {
		List<ArticleCountDTO> list = statisticsDAO.getStatisticsArticleSubjects(map);
		return list;
	}

	public List<LogDTO> getStatisticsLog(String date) {
		List<LogDTO> list = statisticsDAO.getStatisticsLog(date);
		return list;
	}
	
}
