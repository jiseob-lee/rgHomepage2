package com.rg.webclient.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class WebClientService {

	public String createPost() {
		
		WebClient webClient = WebClient.builder()
				.baseUrl("https://jisblee.me")
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE + "; charset=UTF-8")
				.build();
		
		Mono<String> response = webClient.get()
				.uri(uriBuilder -> uriBuilder
						.path("/getBoardContent.do")
						.queryParam("boardArticleIdx", "860")
						.queryParam("pathnam", "/board/view/0/1/860")
						.queryParam("referrer", "")
						.queryParam("lang", "ko")
						.build())
				.retrieve()
				.bodyToMono(String.class);
		
		return response.block();
	}
}
