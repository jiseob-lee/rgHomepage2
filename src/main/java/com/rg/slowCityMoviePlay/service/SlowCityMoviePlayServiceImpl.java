package com.rg.slowCityMoviePlay.service;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rg.manageBoard.service.ManageBoardServiceImpl;
import com.rg.slowCityMoviePlay.dao.SlowCityMoviePlayDAOImpl;
import com.rg.slowCityMoviePlay.dto.SlowCityMoviePlayDTO;
import com.rg.util.GeoLite2;
import com.rg.util.IP;
import com.rg.util.LocaleUtil;

import jakarta.servlet.http.HttpServletRequest;

@Service("slowCityMoviePlayService")
public class SlowCityMoviePlayServiceImpl implements SlowCityMoviePlayService {

	private final Logger logger = LogManager.getLogger(ManageBoardServiceImpl.class);

	@Autowired
	private SlowCityMoviePlayDAOImpl slowCityMoviePlayDAO;
	
	@Override
	public int registSlowCityMoviePlay(HttpServletRequest request) {
		
		SlowCityMoviePlayDTO slowCityMoviePlayDTO = new SlowCityMoviePlayDTO();
		
		slowCityMoviePlayDTO.setIpAddr(IP.getClientIP(request));
		slowCityMoviePlayDTO.setUserAgent(request.getHeader("User-Agent"));
		
		LocaleUtil localeUtil = new LocaleUtil();
		String currentLocale = localeUtil.getLocale().getLanguage();
		
		slowCityMoviePlayDTO.setLanguage(currentLocale);
		
		
		Map<String, String> geoLiteMap = GeoLite2.getIpInfo(slowCityMoviePlayDTO.getIpAddr());
		
		if (geoLiteMap != null) {
			slowCityMoviePlayDTO.setCountry(geoLiteMap.get("country"));
			slowCityMoviePlayDTO.setSubdivision(geoLiteMap.get("subdivision"));
			slowCityMoviePlayDTO.setCity(geoLiteMap.get("city"));
		}
		
		return slowCityMoviePlayDAO.registSlowCityMoviePlay(slowCityMoviePlayDTO);
	}
}
