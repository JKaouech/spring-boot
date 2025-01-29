package tn.jika.crud.sql.web.rest;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tn.jika.crud.sql.model.Car;
import tn.jika.crud.sql.model.User;
import tn.jika.crud.sql.repository.UserRepository;

@Slf4j
@RequiredArgsConstructor
@RestController()
@RequestMapping("/api/mock/users")
public class UserMockController {

	private final UserRepository userRepository;

	@PostMapping("/{nb}")
	public ResponseEntity<HttpStatus> addUsers(@PathVariable int nb) {
		
		for (int i = 1; i <= nb; i++) {
			String id = UUID.randomUUID().toString();
			User user = User.builder()
					.id(id)
					.name("Name-"+i)
					.email(id+"@test.com")
					.build();
			
			Car car1 = Car.builder().id(id+"-A")
					.model("model- "+i+"-A")
					.registration(id+"A")
					.build();
			user.addCar(car1);
			
			Car car2 = Car.builder().id(id+"-B")
					.model("model- "+i+"-B")
					.registration(id+"B")
					.build();
			user.addCar(car2);
			log.info("Save user [{}]", i);
			userRepository.save(user);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
