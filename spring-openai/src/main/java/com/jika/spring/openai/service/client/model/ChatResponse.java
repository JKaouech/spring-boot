package com.jika.spring.openai.service.client.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatResponse {

	private String id;

	private List<Choice> choices;

	@JsonProperty("finish_reason")
	private String finishReason;

}
