package com.jik;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;

@SpringBootApplication
public class SpringActuatorApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(SpringActuatorApplication.class);
		BufferingApplicationStartup startup = new BufferingApplicationStartup(2048);
		startup.addFilter(startupStep -> startupStep.getName().matches("spring.boot.application.starting"));
		app.setApplicationStartup(startup);
		app.run(args);
	}
}
