package tn.jika.crud.sql.web.rest;

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

import lombok.RequiredArgsConstructor;
import tn.jika.crud.sql.exception.ResourceNotFoundException;
import tn.jika.crud.sql.model.Role;
import tn.jika.crud.sql.model.User;
import tn.jika.crud.sql.service.UserService;
import tn.jika.crud.sql.web.mapper.RoleMapper;
import tn.jika.crud.sql.web.mapper.UserMapper;
import tn.jika.crud.sql.web.model.RoleDto;
import tn.jika.crud.sql.web.model.SimpleUserDto;

@RequiredArgsConstructor
@RestController()
@RequestMapping("/api/v1/users")
public class UserController {

	private final UserService userService;
	
	private final UserMapper userMapper;
	
	private final RoleMapper roleMapper;

	@GetMapping
	public ResponseEntity<List<SimpleUserDto>> getAllUser() {
		List<User> users = userService.getAllUser();
		return new ResponseEntity<>(userMapper.userToSimpleDto(users), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<SimpleUserDto> getUser(@PathVariable String id) {
		User user = userService.getUser(id);
		if (user != null) {
			return new ResponseEntity<>(userMapper.userToSimpleDto(user), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/mail/{mail}")
	public ResponseEntity<SimpleUserDto> getUserByMail(@PathVariable String mail) {
		User user = userService.getUserByMail(mail);
		if (user != null) {
			return new ResponseEntity<>(userMapper.userToSimpleDto(user), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/role/{role}")
	public ResponseEntity<List<SimpleUserDto>> getUserByRole(@PathVariable String role) {
		List<User> users = userService.getUserByRole(role);
			return new ResponseEntity<>(userMapper.userToSimpleDto(users), HttpStatus.OK);
	}
	
	
	@PostMapping
	public ResponseEntity<SimpleUserDto> addUser(@RequestBody SimpleUserDto userDto) {
		User user = userService.addUser(userMapper.dtoToUser(userDto));
		userDto.setId(user.getId());
		return new ResponseEntity<>(userDto, HttpStatus.CREATED);
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
	
	@PostMapping("/{id}/role")
	public ResponseEntity<RoleDto> addRole(@PathVariable String id, @RequestBody RoleDto roleDto) {
		Role role = roleMapper.dtoToRole(roleDto);
		role.setUser(User.builder().id(id).build());
		role = userService.addRole(id, roleMapper.dtoToRole(roleDto));
		roleDto.setId(role.getId());
		return new ResponseEntity<>(roleDto, HttpStatus.CREATED);
	}
	
	


}
