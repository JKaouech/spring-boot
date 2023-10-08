package com.jika.spring.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.jika.spring.exception.ResourceNotFoundException;
import com.jika.spring.model.User;
import com.jika.spring.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public List<User> getAllUser() {
		return userRepository.findAll();
	}

	public User getUser(String id) {
		return userRepository.findById(id).orElse(null);
	}

	public User addUser(User user) {
		user.setId(UUID.randomUUID().toString());
		return userRepository.save(user);
	}

	public void deleteUser(String id) throws ResourceNotFoundException {
		User user = getUser(id);
		if (user == null) {
			throw new ResourceNotFoundException("User", "id", id);
		}
		userRepository.delete(user);
	}

}
