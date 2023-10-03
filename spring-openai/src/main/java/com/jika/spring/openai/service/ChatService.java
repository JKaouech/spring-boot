package com.jika.spring.openai.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import com.jika.spring.openai.service.client.model.ChatRequest;
import com.jika.spring.openai.service.client.model.ChatResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {

	@Qualifier("openaiRestTemplate")
	private final RestTemplate restTemplate;

	@Value("${application.openai.model}")
	private String model;

	@Value("${application.openai.url}")
	private String apiUrl;

	public String sendMessage(String message) {
		ChatRequest request = ChatRequest.builder()
				.model(model)
				.message(message)
				.build();

		ChatResponse response = restTemplate.postForObject(apiUrl, request, ChatResponse.class);
		if (response == null || CollectionUtils.isEmpty(response.getChoices())) {
			return "No response";
		}
		return response.getChoices().get(0).getMessage().getContent();
	}

}
