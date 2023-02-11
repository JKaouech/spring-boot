package com.jik.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class TestController {

	final static Logger logger = LoggerFactory.getLogger(TestController.class);

	@GetMapping("/")
	public String root(@RequestParam(value = "name", defaultValue = "World") String name) {
		logger.error(String.format("Hello %s!!", name));
		logger.debug("Debugging log");
		logger.info("Info log");
		logger.warn("Hey, This is a warning!");
		logger.error("Oops! We have an Error. OK");
		return String.format("Hello %s!!", name);
	}

	@GetMapping("/io_task")
	public String io_task()
			throws InterruptedException {
		Thread.sleep(1000);
		logger.info("io_task");
		return "io_task";
	}

	@GetMapping("/cpu_task")
	public String cpu_task() {
		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < 1000; i++) {
			int tmp = i * i * i;
			list.add(tmp);
		}
		logger.info("cpu_task");
		return "cpu_task";
	}

	@GetMapping("/random_sleep")
	public String random_sleep()
			throws InterruptedException {
		Thread.sleep((int) (Math.random() / 5 * 10000));
		logger.info("random_sleep");
		return "random_sleep";
	}

	@GetMapping("/random_status")
	public ResponseEntity<String> random_status() throws InterruptedException {
		List<Integer> givenList = Arrays.asList(200, 200, 300, 400, 500);
		Random rand = new Random();
		int randomElement = givenList.get(rand.nextInt(givenList.size()));
		logger.info("random_status");
		return new ResponseEntity<>("random_status", HttpStatus.valueOf(randomElement));
	}

	@GetMapping("/chain")
	public String chain(){
		RestTemplate restTemplate = new RestTemplate();
		logger.debug("chain is starting");
		restTemplate.getForObject("http://localhost:8080/", String.class);
		restTemplate.getForObject("http://localhost:8080/io_task", String.class);
		restTemplate.getForObject("http://localhost:8080/cpu_task", String.class);
		logger.debug("chain is finished");
		return "chain";
	}

	@GetMapping("/error_test")
	public String error_test() throws Exception {
		throw new Exception("Error test");
	}

}
