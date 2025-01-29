package tn.jika.crud.sql.web.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import tn.jika.crud.sql.model.User;
import tn.jika.crud.sql.service.UserService;
import tn.jika.crud.sql.web.mapper.UserMapper;
import tn.jika.crud.sql.web.model.UserDto;

@RequiredArgsConstructor
@RestController()
@RequestMapping("/api/v2/users")
public class UserControllerV2 {

	private final UserService userService;
	
	private final UserMapper userMapper;

	@PostMapping
	public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto) {
		User user = userService.addUser(userMapper.dtoToUser(userDto));
		userDto.setId(user.getId());
		return new ResponseEntity<>(userDto, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<UserDto>> getAllUser() {
		List<User> users = userService.getAllUser();
		return new ResponseEntity<>(userMapper.userToDto(users), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserDto> getUser(@PathVariable String id) {
		User user = userService.getUser(id);
		if (user != null) {
			return new ResponseEntity<>(userMapper.userToDto(user), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/mail/{mail}")
	public ResponseEntity<UserDto> getUserByMail(@PathVariable String mail) {
		User user = userService.getUserByMail(mail);
		if (user != null) {
			return new ResponseEntity<>(userMapper.userToDto(user), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/car/registration/{registration}")
	public ResponseEntity<UserDto> getUserByCarRegistration(@PathVariable String registration) {
		User user = userService.getUserByCarRegistration(registration);
		if (user != null) {
			return new ResponseEntity<>(userMapper.userToDto(user), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/car/model/{model}")
	public ResponseEntity<List<UserDto>> getUserByCar(@PathVariable String model) {
		List<User> users = userService.getUserByCarModel(model);
		return new ResponseEntity<>(userMapper.userToDto(users), HttpStatus.OK);
	}
	

}
