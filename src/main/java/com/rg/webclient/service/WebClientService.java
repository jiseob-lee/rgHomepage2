package com.rg.webclient.service;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.rg.webclient.dto.ApiException;
import com.rg.webclient.dto.ApiResponse;
import com.rg.webclient.dto.ApiResponse2;
import com.rg.webclient.dto.BusinessException;
import com.rg.webclient.dto.InternalErrorCode;

import reactor.core.publisher.Mono;

@Service
public class WebClientService {

	public Mono<ApiResponse2> createGet() {
		
		WebClient webClient = WebClient.builder()
				.baseUrl("http://localhost:8080")
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE + "; charset=UTF-8")
				.build();
		
		Mono<ApiResponse2> response = webClient.get()
				.uri(uriBuilder -> uriBuilder
						.path("/getBoardContent.do")
						.queryParam("boardArticleIdx", "860")
						.queryParam("pathnam", "/board/view/0/1/860")
						.queryParam("referrer", "")
						.queryParam("lang", "ko")
						.build())
				.retrieve()
				.onStatus(HttpStatusCode::is4xxClientError, resp -> 
					resp.bodyToMono(String.class)
						.flatMap(body -> Mono.error(new IllegalArgumentException("4xx 에러 : " + body)))
					)
				.onStatus(HttpStatusCode::is5xxServerError, resp -> 
					resp.bodyToMono(String.class)
						.flatMap(body -> Mono.error(new IllegalStateException("5xx 에러 : " + body)))
					)
				.bodyToMono(ApiResponse2.class);
				//.bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {});
		
		return response;
		//return response.map(map -> new HashMap<String, Object>(map));
		//rawMonoMap.map(map -> (Map<String, Object>) map);
		//return response.block();
	}

	public Mono<String> createPost(Map<String, String> request) {
		
		//ObjectMapper objectMapper = new ObjectMapper();

        // Convert the record object to a Map
        //@SuppressWarnings("unchecked")
		//Map<String, String> map = objectMapper.convertValue(request, Map.class);
        
		WebClient webClient = WebClient.builder()
				.baseUrl("http://localhost:8080")
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE + "; charset=UTF-8")
				.build();
		
		Mono<String> response = webClient.post()
				.uri(uriBuilder -> {
					uriBuilder.path("/getBoardListCount.do");
					request.forEach(uriBuilder::queryParam);
					return uriBuilder.build();
				})
				.bodyValue(request)
				.retrieve()
				.onStatus(HttpStatusCode::is4xxClientError, resp -> 
					resp.bodyToMono(String.class)
						.flatMap(body -> Mono.error(new IllegalArgumentException("4xx 에러 : " + body)))
					)
				.onStatus(HttpStatusCode::is5xxServerError, resp -> 
					resp.bodyToMono(String.class)
						.flatMap(body -> Mono.error(new IllegalStateException("5xx 에러 : " + body)))
					)
				.bodyToMono(String.class)
				.flatMap(resp -> {
					if (!StringUtils.isNumeric(resp)) {
						return Mono.error(new BusinessException("에러 발생"));
					}
					return Mono.just(resp);
				});
		
		return response;
		//return response.block();
	}

	public Mono<String> createPost2(Map<String, String> request) {
		
		WebClient webClient = WebClient.builder()
				.baseUrl("http://localhost:8080")
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE + "; charset=UTF-8")
				.build();
		
		Mono<String> response = webClient.post()
				.uri("/createResponse.do")
				.bodyValue(request)
				.retrieve()
				.onStatus(HttpStatusCode::is4xxClientError, resp -> 
					resp.bodyToMono(String.class)
						.flatMap(body -> Mono.error(new IllegalArgumentException("4xx 에러 : " + body)))
					)
				.onStatus(HttpStatusCode::is5xxServerError, resp -> 
					resp.bodyToMono(String.class)
						.flatMap(body -> Mono.error(new IllegalStateException("5xx 에러 : " + body)))
					)
				.bodyToMono(ApiResponse.class)
				.flatMap(resp -> {
					if (!"OK".equals(resp.resultCode())) {
						return Mono.error(new ApiException(InternalErrorCode.EXTERNAL_ERROR));
					}
					return Mono.just(resp.data());
				});
		
		return response;
		//return response.block();
	}
}
