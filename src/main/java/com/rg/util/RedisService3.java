package com.rg.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.rg.login.dto.UserDetailsVO;

@Component
public class RedisService3 {

	private static final Logger logger = LoggerFactory.getLogger(RedisService3.class);
	
	@Autowired
	StringRedisTemplate redisTemplate;

	public void insertRedisMap(String redisKey, Map<String, Object> map) {

		HashOperations<String, Object, Object> stringObjectObjectHashOperations = redisTemplate.opsForHash();
		
		//Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			//System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			
			stringObjectObjectHashOperations.put(redisKey, entry.getKey(), entry.getValue());
		}

		//stringObjectObjectHashOperations.put(redisKey, "Hello", "rg1");
		//stringObjectObjectHashOperations.put(redisKey, "Hello2", "rg2");
		//stringObjectObjectHashOperations.put(redisKey, "Hello3", "rg3");
		
	}

	public void processRedisLogout(String redisKey) {
		Set<String> keys = redisTemplate.keys(redisKey + "*");
		for (String key : keys) {
			redisTemplate.delete(key);
		}
	}
	
	public int selectRedisLikeSessionCount(String redisKey) {
		int count = 0;
		Set<String> keys = redisTemplate.keys(redisKey + "*");
		for (String key : keys) {
			count++;
		}
		return count;
	}
	
	public List<UserDetailsVO> selectPrevLoginVOList(String redisKey) {
		
		List<UserDetailsVO> list = new ArrayList<>();
		
		if (redisKey == null || redisKey.trim().equals("")) {
			return null;
		}
		
		Set<String> keys = redisTemplate.keys(redisKey + "*");
		for (String key : keys) {
			UserDetailsVO vo = selectRedisSession(key);
			list.add(vo);
		}
		
		return list;
	}
	
	public void deleteRedisLikeSession(String redisKey) {
		Set<String> keys = redisTemplate.keys(redisKey + "*");
		for (String key : keys) {
			redisTemplate.delete(key);
		}
	}
	
	public void setTimeOutSecond(String key, Integer sessionTime) {
		redisTemplate.expire(key, sessionTime, TimeUnit.SECONDS);
	}
	
	@SuppressWarnings("unchecked")
	public UserDetailsVO selectRedisSession(String key) {
		
		HashOperations<String, Object, Object> stringObjectObjectHashOperations = redisTemplate.opsForHash();
		Map<Object, Object> entries = stringObjectObjectHashOperations.entries(key);
		
		UserDetailsVO vo = new UserDetailsVO();
		vo.setLoginId(String.valueOf(entries.get("loginId")));
		vo.setLoginUserId1(String.valueOf(entries.get("loginUserId1")));
		vo.setLoginUserName(String.valueOf(entries.get("loginUserName")));
		
		return vo;
	}
	
	public Set<String> selectKey(String key) {
		Set<String> set = new HashSet<>();
		
		SetOperations<String, String> stringStringSetOperations = redisTemplate.opsForSet();

		Cursor<String> cursor = stringStringSetOperations.scan(key, ScanOptions.scanOptions().match("*").build());

		while (cursor.hasNext()) {
			//logger.debug("cursor = " + cursor.next());
			set.add(cursor.next());
		}
		
		return set;
	}
}
