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
import tn.jika.crud.sql.model.Car;
import tn.jika.crud.sql.model.User;
import tn.jika.crud.sql.service.UserService;
import tn.jika.crud.sql.web.mapper.CarMapper;
import tn.jika.crud.sql.web.mapper.UserMapper;
import tn.jika.crud.sql.web.model.CarDto;
import tn.jika.crud.sql.web.model.SimpleUserDto;

@RequiredArgsConstructor
@RestController()
@RequestMapping("/api/v1/users")
public class UserController {

	private final UserService userService;

	private final UserMapper userMapper;

	private final CarMapper carMapper;

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

	@GetMapping("/car/registration/{registration}")
	public ResponseEntity<SimpleUserDto> getUserByCarRegistration(@PathVariable String registration) {
		User user = userService.getUserByCarRegistration(registration);
		if (user != null) {
			return new ResponseEntity<>(userMapper.userToSimpleDto(user), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/car/model/{model}")
	public ResponseEntity<List<SimpleUserDto>> getUserByCar(@PathVariable String model) {
		List<User> users = userService.getUserByCarModel(model);
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

	@PostMapping("/{id}/car")
	public ResponseEntity<CarDto> addCar(@PathVariable String id, @RequestBody CarDto carDto) {
		Car car = carMapper.dtoToCar(carDto);
		car.setOwner(User.builder().id(id).build());
		car = userService.addCar(id, carMapper.dtoToCar(carDto));
		carDto.setId(car.getId());
		return new ResponseEntity<>(carDto, HttpStatus.CREATED);
	}

}
