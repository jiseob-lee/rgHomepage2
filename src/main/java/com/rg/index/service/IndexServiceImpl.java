package com.rg.index.service;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rg.index.dao.IndexDAOImpl;
import com.rg.index.dto.IndexDTO;
import com.rg.util.GeoLite2;

@Service("indexService")
public class IndexServiceImpl implements IndexService {

	private final Logger logger = LogManager.getLogger(IndexServiceImpl.class);
	
	@Autowired
	private IndexDAOImpl indexDAO;

	@Override
	public void insertAccessLog(IndexDTO indexDTO) {
		
		try {
			
			Map<String, String> geoMap = GeoLite2.getIpInfo(indexDTO.getRemoteAddr());
			
			InetAddress ipAddress = InetAddress.getByName(indexDTO.getRemoteAddr());

			if ("127.0.0.1".equals(ipAddress.getHostAddress()) || "0:0:0:0:0:0:0:1".equals(ipAddress.getHostAddress())) {
				return;
			}
			
			indexDTO.setCountry(geoMap == null ? "" : geoMap.get("country"));
			indexDTO.setSubdivision(geoMap == null ? "" : geoMap.get("subdivision"));
			indexDTO.setCity(geoMap == null ? "" : geoMap.get("city"));
			
			indexDAO.insertAccessLog(indexDTO);
			
			logger.debug("#^##");
			logger.debug("#^########################### Hash : " + indexDTO.getHash());
			logger.debug("#^########################### Referer : " + indexDTO.getReferer());
			logger.debug("#^########################### RemoteAddr : " + indexDTO.getRemoteAddr());
			logger.debug("#^########################### UserAgent : " + indexDTO.getUserAgent());
			logger.debug("#^########################### Language : " + indexDTO.getLanguage());
			logger.debug("#^########################### Country : " + indexDTO.getCountry());
			logger.debug("#^##");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
