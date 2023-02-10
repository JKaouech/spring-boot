package com.jik;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import de.codecentric.boot.admin.server.config.AdminServerHazelcastAutoConfiguration;
import de.codecentric.boot.admin.server.config.EnableAdminServer;

@EnableAdminServer

@SpringBootApplication(exclude = AdminServerHazelcastAutoConfiguration.class)
public class SpringAdminServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringAdminServerApplication.class, args);
	}

}
