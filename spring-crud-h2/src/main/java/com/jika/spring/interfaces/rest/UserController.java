package com.jika.spring.interfaces.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jika.spring.exception.ResourceNotFoundException;
import com.jika.spring.model.User;
import com.jika.spring.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController()
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	@GetMapping
	public ResponseEntity<List<User>> getAllUser() {
		List<User> users = userService.getAllUser();
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> getUser(@PathVariable String id) {
		User user = userService.getUser(id);
		if (user != null) {
			return new ResponseEntity<>(user, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping
	public ResponseEntity<User> addUser(@RequestBody User user) {
		user = userService.addUser(user);
		return new ResponseEntity<>(user, HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deleteUser(@PathVariable String id) {
		try {
			userService.deleteUser(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
