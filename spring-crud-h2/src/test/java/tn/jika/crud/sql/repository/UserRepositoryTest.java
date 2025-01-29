package tn.jika.crud.sql.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import tn.jika.crud.sql.model.User;
import tn.jika.crud.sql.repository.UserRepository;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	private String userId;
	private String name;
	User user;

	@BeforeAll
	public void setUp() {
		userId = "id-01";
		name = "JIKA";

		user = User.builder().id(userId).name(name).email("jika@test.com").build();
		user = userRepository.save(user);
	}
	

	@Test
	public void testCreateOperations() {
		assertNotNull(user.getId());
	}

	@Test
	public void testReadOperations() {
		User retrievedPerson = userRepository.findById(userId).orElse(null);
		assertEquals(name, retrievedPerson.getName());
	}

	@Test
	public void testUpdateOperations() {
		User retrievedUser = userRepository.findById(userId).orElse(null);
		retrievedUser.setEmail(null);
		userRepository.save(retrievedUser);
		User updatedUser = userRepository.findById(userId).orElse(null);
		assertEquals(null, updatedUser.getEmail());

	}

	@Test
	public void testDeleteOperations() {
		userRepository.deleteById(userId);
		User deletedPerson = userRepository.findById(userId).orElse(null);
		assertEquals(null, deletedPerson);
	}
}