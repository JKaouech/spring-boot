package com.jika.spring.openai.service.client.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRequest {

	private String model;

	private List<Message> messages;

	// number of responses to generate
	private int n;

	// controls the randomness of the response
	private double temperature;

	@Builder
	public ChatRequest(String model, String message) {
		this.model = model;
		this.messages = new ArrayList<>();
		this.messages.add(new Message("user", message));
		this.n = 1;
		this.temperature = 1;
	}

}
