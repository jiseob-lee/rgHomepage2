package com.rg.login.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.rg.login.dto.UserDetailsVO;
import com.rg.login.service.LoginService;
import com.rg.util.AES256Util;
import com.rg.util.CookieHandle;
import com.rg.util.GeoLite2;
import com.rg.util.IP;
import com.rg.util.RedisService3;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

	private final Logger logger = LogManager.getLogger(CustomLoginSuccessHandler.class);

	//@Autowired
	//@Qualifier("sessionRegistry")
	//private SessionRegistry sessionRegistry;

	//@Autowired
	//private RedisTemplate<String, Object> redisTemplate;

	@Autowired
	private LoginService loginService;

	@Autowired
	private RedisService3 redisService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		/*
		 * if (authentication != null && authentication.getDetails() != null) { try {
		 * request.getSession().invalidate(); } catch (Exception e) {
		 * e.printStackTrace(); } }
		 */
		String userId = (String)authentication.getPrincipal();
		logger.info("####################### login userId : " + userId);
		logger.info("# 3 #### # ################# login name : " + authentication.getName());
		
		String host = request.getRemoteHost();
		logger.info("#### host : " + host);
		
		String loginId = authentication.getName();
		
		String loginPw = loginService.getLoginPwEncrypted(loginId);
		
		String loginName = loginService.getUserName(loginId);

		HttpSession session = request.getSession();

		String sId = session.getId();

		logger.info("####################### autoLogin : " + request.getParameter("autoLogin"));
		
		AES256Util aes256Util = new AES256Util();
		String loginIdEnc = null;
		try {
			loginIdEnc = aes256Util.encrypt(loginId + "||" + loginPw);
		} catch (UnsupportedEncodingException | GeneralSecurityException e) {
			e.printStackTrace();
		}
		Cookie cookie = new Cookie("loginId", loginIdEnc); // 쿠키 이름 지정하여 생성( key, value 개념)
	    cookie.setMaxAge(60*60*24*100); //쿠키 유효 기간: 하루로 설정(60초 * 60분 * 24시간)
	    cookie.setPath("/"); //모든 경로에서 접근 가능하도록 설정
	    cookie.setHttpOnly(true);
	    cookie.setSecure(true);
	    
	    Cookie cookie2 = new Cookie("autoLogin", "1"); // 쿠키 이름 지정하여 생성( key, value 개념)
	    cookie2.setMaxAge(60*60*24*100); //쿠키 유효 기간: 하루로 설정(60초 * 60분 * 24시간)
	    cookie2.setPath("/"); //모든 경로에서 접근 가능하도록 설정
	    cookie2.setHttpOnly(true);
	    cookie2.setSecure(true);
	    
		Cookie[] cookies = request.getCookies();
		String autoLogin = "0";
		
	    if (cookies != null) {
	        for (Cookie c : cookies) {
	            String name = c.getName(); // 쿠키 이름 가져오기
	            String value = c.getValue(); // 쿠키 값 가져오기
	            if (name.equals("autoLogin")) {
	            	autoLogin = value;
	            }
	        }
	    }
	    
	    if ("1".equals(autoLogin)) {
	    	response.addCookie(cookie); //response에 Cookie 추가
	    	response.addCookie(cookie2);
	    	// 접속한 IP 정보 DB에 등록, IP 가 일치하지 않으면 자동 로그인 해지
	    }

		//if (session == null) {
			//logger.info("########################## session is null.");
		//} else {
			//session.setAttribute("loginUserId1", userId);
			session.setAttribute("loginId", loginId);

			//session.setAttribute("redisLoginId", "^" + loginId);

			session.setAttribute("loginUserName", loginName);
			
			UserDetailsVO userDetailsVO = new UserDetailsVO();
			int sessionTime = 60 * 60 * 10;
			
			userDetailsVO.setLoginId(loginId);
			userDetailsVO.setLoginUserId1(userId);
			userDetailsVO.setLoginUserName(loginName);
			
			redisService.deleteRedisLikeSession("LOGIN||SESSION||" + userDetailsVO.getLoginId() + "||*");
			
			// redis 세션 저장
			String redisKey = "LOGIN||SESSION||" + userDetailsVO.getLoginId() + "||" + session.getId();
			redisService.insertRedisMap(redisKey, new HashMap<String, Object>(userDetailsVO.getMap()));
			redisService.setTimeOutSecond(redisKey, sessionTime);
			
			CookieHandle.setCookie(response, "login_id", userDetailsVO.getLoginId());
			CookieHandle.setCookie(response, "session_id", session.getId());
			
			logger.info("#1#### # ############### session sId : " + sId);
			//logger.info("################################ session loginUserId1 : "
					//+ (String) session.getAttribute("loginUserId1"));
			//logger.info("################################ session getUuidCount 1 : "
					//+ (Integer) session.getAttribute("getUuidCount"));
		//}

		// 로그인 이력 기록
		
		Map<String, String> loginMap = new HashMap<>();
		
		loginMap.put("loginId", userDetailsVO.getLoginId());
		loginMap.put("loginResult", "success");
		loginMap.put("ip", IP.getClientIP(request));
		
		Map<String, String> ipInfo = GeoLite2.getIpInfo(request);
		loginMap.put("country", ipInfo.get("country"));
		loginMap.put("subdivision", ipInfo.get("subdivision"));
		loginMap.put("city", ipInfo.get("city"));
		
		loginService.putLoginHistory(loginMap);
		
		//redisTemplate.setKeySerializer(new StringRedisSerializer());
		//redisTemplate.setValueSerializer(new StringRedisSerializer());

		//Set<String> keys = redisTemplate.keys("spring:session:sessions:*");

		//JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		//JedisPool pool = new JedisPool(jedisPoolConfig, "127.0.0.1", 6379, 1000);
		// Jedis풀 생성(JedisPoolConfig, host, port, timeout, password)
		//Jedis jedis = pool.getResource();// thread, db pool처럼 필요할 때마다 getResource()로 받아서 쓰고 다 쓰면 close로 닫아야 한다.

		//for (String key : keys) {
			// redisTemplate.opsForHash()
			//if (key.indexOf("expires") == -1) {
				//logger.info("#1#### # ############### key : " + key);

				//Map<Object, Object> map = redisTemplate.opsForHash().entries(key);
				//for (Iterator<Object> itr = map.keySet().iterator(); itr.hasNext();) {
					//String k = (String) itr.next();
					//logger.info("#1#### # ############### k : " + k);
					// if ("sessionAttr:loginId".equals(k) && !key.equals("spring:session:sessions:"
					// + sId)) {
					//if ("sessionAttr:redisLoginId".equals(k) && !key.equals("spring:session:sessions:" + sId)) {
						// String v = (String)map.get(k);
						//String v = (String) redisTemplate.opsForHash().get(key, k);

						//logger.info("#1#### # ############### v1 : " + v);
						//try {
							//logger.info("#1#### # ############### v2 : " + getHashData(key, k, java.lang.String.class));
						//} catch (Exception e) {
							//e.printStackTrace();
						//}
						//if (v.endsWith("^" + loginId)) {
							//logger.info("#1#### ################ v : " + v);
							//redisTemplate.delete(key);
						//}
					//}
				//}

				/*
				 * Map<String, String> map = jedis.hgetAll(key); for(Iterator<String> itr =
				 * map.keySet().iterator(); itr.hasNext(); ) { String k = itr.next();
				 * logger.info("#1#################### map : " + k); if
				 * ("sessionAttr:loginId".equals(k)) { String v = (String)map.get(k);
				 * logger.info("#1#### # ############### v : " + v); //if
				 * (v.endsWith(loginName)) { //logger.info("#1#### ################ v : " + v);
				 * //redisTemplate.delete(key); //} } }
				 */
			//}

			// Map<String, String> map = jedis.hgetAll(key);

			// String loginId = map.get("")
			// for(Iterator<String> itr = map.keySet().iterator(); itr.hasNext(); ) {
			// itr.next() 는 키값(name, age, job)
			// String k = itr.next();
			// logger.info("#1#################### map : " + k + " : " + map.get(k)); //키에
			// 해당하는 값
			// }
			// 출처: https://aljjabaegi.tistory.com/476 [알짜배기 프로그래머]
		//}

		//if (jedis != null) {
			//jedis.close();
		//}
		//pool.close();

		/*
		 * List<Object> principals = sessionRegistry.getAllPrincipals();
		 * 
		 * //List<String> usersNamesList = new ArrayList<String>();
		 * 
		 * 
		 * for (Object principal: principals) {
		 * 
		 * //CustomUserDetails customUserDetails = (CustomUserDetails)principal;
		 * 
		 * logger.info("#1################ principal : " + principal);
		 * 
		 * boolean includeExpiredSessions = false; List<SessionInformation> sessionList
		 * = sessionRegistry.getAllSessions(principal, includeExpiredSessions);
		 * 
		 * for (SessionInformation sessionInfo : sessionList) { String sessionId =
		 * sessionInfo.getSessionId();
		 * 
		 * //sessionRegistry.getSessionInformation(sessionId).expireNow();
		 * 
		 * // https://stackoverflow.com/questions/43090679/spring-boot-redis-how-to-
		 * invalidate-all-sessions-of-a-user
		 * 
		 * if (loginName.equals(principal) && !sessionId.equals(sId)) {
		 * 
		 * logger.info("#1################ sessionId : " + sessionId);
		 * 
		 * boolean result = redisTemplate.delete("spring:session:sessions:" +
		 * sessionId);
		 * 
		 * logger.info("#1################ result : " + result);
		 * 
		 * //sessionInfo.expireNow(); }
		 * 
		 * //sessionRegistry.removeSessionInformation(sessionId);
		 * 
		 * 
		 * //JedisPoolConfig jedisPoolConfig = new JedisPoolConfig(); //JedisPool pool =
		 * new JedisPool(jedisPoolConfig, "127.0.0.1", 6379, 1000); //Jedis풀
		 * 생성(JedisPoolConfig, host, port, timeout, password) //Jedis jedis =
		 * pool.getResource();//thread, db pool처럼 필요할 때마다 getResource()로 받아서 쓰고 다 쓰면
		 * close로 닫아야 한다. //Set<String> keys1 = jedis.keys("spring:session:sessions:" +
		 * sessionId); //logger.info("################# keys.size : " + keys1.size());
		 * 
		 * //logger.info("################# redisTemplate.toString : " +
		 * redisTemplate.toString());
		 * 
		 * //redisTemplate.setDefaultSerializer(new StringRedisSerializer());
		 * 
		 * //logger.info("################# redisTemplate.hasKey : " +
		 * redisTemplate.hasKey("spring:session:expirations:1595831280000"));
		 * 
		 * //Set<String> keys = redisTemplate.keys("spring:session:sessions:" +
		 * sessionId); //Set<String> keys = redisTemplate.keys("*");
		 * //logger.info("################# keys.size : " + keys.size()); //for (String
		 * key : keys) { //logger.info("################### key : " + key); //long l =
		 * jedis.del(key); //logger.info("################### l : " + l);
		 * //jedis.del(keys.toArray(new String[keys.size()])); //boolean result =
		 * redisTemplate.delete(key); //logger.info("################### result : " +
		 * result); //}
		 * 
		 * //jedis.del(keys.toArray(new String[keys.size()]));
		 * 
		 * //if( jedis != null ){ //jedis.close(); //} //pool.close(); }
		 * 
		 * 
		 * //if (principal instanceof User) { //usersNamesList.add(((User)
		 * principal).getUsername()); //}
		 * 
		 * }
		 */

		// boolean result = redisTemplate.delete("spring:session:sessions:" + sId);
		// logger.info("################# result : " + result);
		// result =
		// redisTemplate.delete("spring:session:sessions:4c894448-d775-4d1b-870b-28574d4b53e2");
		// logger.info("################# result : " + result);
		// logger.info("################# redisTemplate.hasKey : " +
		// redisTemplate.hasKey("spring:session:sessions:" + sId));

		response.sendRedirect(loginId.equals("rg111") ? "/" : "/rg/");
	}


	//public <T> T getData(String key, Class<T> classType) throws Exception {
		//String jsonResult = (String) redisTemplate.opsForValue().get(key);
		//if (StringUtils.isBlank(jsonResult)) {
			//return null;
		//} else {
			//ObjectMapper mapper = new ObjectMapper();
			//T obj = mapper.readValue(jsonResult, classType);
			//return obj;
		//}
	//}

	//public String getHashData(String key, String hashKey, Class<String> classType) throws Exception {

		//String jsonResult = (String) redisTemplate.opsForHash().get(key, hashKey);
		//logger.info("#2######## " + jsonResult);
		//logger.info("#2######## " + jsonResult.getBytes());
		//logger.info("#2######## " + jsonResult.replaceAll("([\\ud800-\\udbff\\udc00-\\udfff])", ""));
		//logger.info("#2######## " + removeBadChars(jsonResult));
		//logger.info("#2######## " + jsonResult.replaceAll("[^A-Za-z0-9]", ""));
		
		//Jackson2JsonRedisSerializer<String> serializer = new Jackson2JsonRedisSerializer<String>(String.class);
		//serializer.setObjectMapper(new ObjectMapper());
		//return serializer.deserialize(jsonResult.replaceAll("0x..", "").getBytes());

		// String jsonResult = (String) redisTemplate.opsForHash().get(key, hashKey);
		// if (StringUtils.isBlank(jsonResult)) {
		// return null;
		// } else {
		// ObjectMapper mapper = new ObjectMapper();
		// T obj = mapper.readValue(jsonResult, classType);
		// return obj;
		// }
	//}

	//public String removeBadChars(String s) {
		//if (s == null)
			//return null;
		//StringBuilder sb = new StringBuilder();
		//for (int i = 0; i < s.length(); i++) {
			//if (Character.isHighSurrogate(s.charAt(i)) || Character.isLowSurrogate(s.charAt(i)))
				//continue;
			//sb.append(s.charAt(i));
		//}
		//return sb.toString();
	//}
}

