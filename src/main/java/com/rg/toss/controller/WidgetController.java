package com.rg.toss.controller;

/*
import jakarta.servlet.http.HttpServletRequest;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Set;
*/

//@Controller
public class WidgetController {

	/*
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/toss/confirm.do")
    public ResponseEntity<JSONObject> confirmPayment(@RequestBody String jsonBody) throws Exception {

        JSONParser parser = new JSONParser();
        String orderId;
        String amount;
        String paymentKey;
        try {
            // 클라이언트에서 받은 JSON 요청 바디입니다.
            JSONObject requestData = (JSONObject) parser.parse(jsonBody);
            paymentKey = (String) requestData.get("paymentKey");
            orderId = (String) requestData.get("orderId");
            amount = (String) requestData.get("amount");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        };
        JSONObject obj = new JSONObject();
        obj.put("orderId", orderId);
        obj.put("amount", amount);
        obj.put("paymentKey", paymentKey);

        // TODO: 개발자센터에 로그인해서 내 결제위젯 연동 키 > 시크릿 키를 입력하세요. 시크릿 키는 외부에 공개되면 안돼요.
        // @docs https://docs.tosspayments.com/reference/using-api/api-keys
        //String widgetSecretKey = "test_gsk_docs_OaPz8L5KdmQXkzRz3y47BMw6";
        String widgetSecretKey = "test_gsk_Gv6LjeKD8aP0R10qoQ7YrwYxAdXy";
        
        // 토스페이먼츠 API는 시크릿 키를 사용자 ID로 사용하고, 비밀번호는 사용하지 않습니다.
        // 비밀번호가 없다는 것을 알리기 위해 시크릿 키 뒤에 콜론을 추가합니다.
        // @docs https://docs.tosspayments.com/reference/using-api/authorization#%EC%9D%B8%EC%A6%9D
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encodedBytes = encoder.encode((widgetSecretKey + ":").getBytes("UTF-8"));
        String authorizations = "Basic " + new String(encodedBytes, 0, encodedBytes.length);

        JSONObject jsonObject = null;
        int code = 0;
        
        try {
        
        	logger.info("#### test test.....");
        
        // 결제 승인 API를 호출하세요.
        // 결제를 승인하면 결제수단에서 금액이 차감돼요.
        // @docs https://docs.tosspayments.com/guides/payment-widget/integration#3-결제-승인하기
        URL url = new URL("https://api.tosspayments.com/v1/payments/confirm");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Authorization", authorizations);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);


        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(obj.toString().getBytes("UTF-8"));

        code = connection.getResponseCode();
        boolean isSuccess = code == 200 ? true : false;

        InputStream responseStream = isSuccess ? connection.getInputStream() : connection.getErrorStream();

        // TODO: 결제 성공 및 실패 비즈니스 로직을 구현하세요.
        Reader reader = new InputStreamReader(responseStream, StandardCharsets.UTF_8);
        jsonObject = (JSONObject) parser.parse(reader);
        responseStream.close();
        
        logger.info("#### jsonObject : " + jsonObject.toJSONString());
        
        //int age = jsonObject.getString("age");

        printJson("", jsonObject);
        
        } catch (Exception e) {
        	logger.info("#### test test..... " + e.getMessage());
        	//System.out.println(e.getMessage());
        	e.printStackTrace();
        }
        
        return ResponseEntity.status(code).body(jsonObject);
    }

    public void printJson(String key1, JSONObject jsonObject) {
    	
    	JSONParser parser = new JSONParser();
    	
    	@SuppressWarnings("unchecked")
	    Set<String> keySet = jsonObject.keySet();
	    for (String key : keySet) {
	        Object value = jsonObject.get(key);
	        String type = value == null ? "null" : value.getClass().getSimpleName();
	        logger.info("{} > {} = {} ({})", key1, key, value, type);
	        
	        if ("issuerCode".equals(key)) {
	        	logger.info("#### 카드사 : " + CommonCode.card.get(value));
	        }
	        
	        if ("JSONObject".equals(type)) {
	        	JSONObject jsonObject1 = null;
				try {
					jsonObject1 = (JSONObject) parser.parse(String.valueOf(value));
				} catch (ParseException e) {
					e.printStackTrace();
				}
	        	printJson(key, jsonObject1);
	        }
	    }
    }

    /*
     * 인증성공처리
     * @param request
     * @param model
     * @return
     * @throws Exception
     /
    @RequestMapping(value = "/toss/success.do", method = RequestMethod.GET)
    public String paymentRequest(HttpServletRequest request, Model model) throws Exception {
        return "/toss/success";
    }

    @RequestMapping(value = "/toss/checkout.do", method = RequestMethod.GET)
    public String index(HttpServletRequest request, Model model) throws Exception {
        return "/toss/checkout";
    }

    /*
     * 인증실패처리
     * @param request
     * @param model
     * @return
     * @throws Exception
     /
    @RequestMapping(value = "/toss/fail.do", method = RequestMethod.GET)
    public String failPayment(HttpServletRequest request, Model model) throws Exception {
        String failCode = request.getParameter("code");
        String failMessage = request.getParameter("message");

        model.addAttribute("code", failCode);
        model.addAttribute("message", failMessage);

        return "/toss/fail";
    }
    */
}
