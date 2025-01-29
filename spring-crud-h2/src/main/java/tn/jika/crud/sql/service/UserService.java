package tn.jika.crud.sql.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tn.jika.crud.sql.exception.ResourceNotFoundException;
import tn.jika.crud.sql.model.Role;
import tn.jika.crud.sql.model.User;
import tn.jika.crud.sql.repository.RoleRepository;
import tn.jika.crud.sql.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	
	private final RoleRepository roleRepository;

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

	public Role addRole(Role role) {
		role.setId(UUID.randomUUID().toString());
		return roleRepository.save(role);
	}

	public Role addRole(String userId, Role role) {
		User user = getUser(userId);
		if(user != null) {
			user.addRole(role);
		}
		addRole(role);
		return role;
	}

	public User getUserByMail(String mail) {
		return userRepository.findByEmail(mail).orElse(null);
	}

	public List<User> getUserByRole(String role) {
		return userRepository.findUsersByRoleName(role);
	}

}
