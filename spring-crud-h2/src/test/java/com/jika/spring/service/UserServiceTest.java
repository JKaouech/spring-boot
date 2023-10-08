package com.jika.spring.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.jika.spring.model.User;
import com.jika.spring.repository.UserRepository;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest {

	@MockBean
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	private String name;
	private String email;

	@BeforeAll
	public void setUp() {
		name = "JIKA";
		email = "jika@test.com";
	}

	@Test
	public void testSaveUser() {
		// Arrange
		User userToSave = User.builder().name(name).email(email).build();
		when(userRepository.save(any())).thenReturn(userToSave);

		// Act
		User savedUser = userService.addUser(userToSave);

		// Assert
		assertEquals(name, savedUser.getName());
		assertEquals(email, savedUser.getEmail());

		verify(userRepository, times(1)).save(any());
	}

	@Test
	public void testGetUserById() {
		// Arrange
		String userId = "id-01";
		User userInDatabase = User.builder().id(userId).name(name).email(email).build();
		when(userRepository.findById(userId)).thenReturn(Optional.of(userInDatabase));

		// Act
		User retrievedUser = userService.getUser(userId);

		// Assert
		assertNotNull(retrievedUser);
		assertEquals(userId, retrievedUser.getId());
		assertEquals(name, retrievedUser.getName());

		verify(userRepository, times(1)).findById(userId);
	}

	// Similar tests for getAllUsers() and deleteUser() can be written
}