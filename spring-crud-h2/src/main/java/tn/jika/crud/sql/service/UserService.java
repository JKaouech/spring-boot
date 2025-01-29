package tn.jika.crud.sql.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tn.jika.crud.sql.exception.ResourceNotFoundException;
import tn.jika.crud.sql.model.Car;
import tn.jika.crud.sql.model.User;
import tn.jika.crud.sql.repository.CarRepository;
import tn.jika.crud.sql.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	
	private final CarRepository carRepository;

	public List<User> getAllUser() {
		return userRepository.findAllWithCars();
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

	public Car addCar(Car car) {
		car.setId(UUID.randomUUID().toString());
		return carRepository.save(car);
	}

	public Car addCar(String userId, Car car) {
		User user = getUser(userId);
		if(user != null) {
			user.addCar(car);
		}
		addCar(car);
		return car;
	}

	public User getUserByMail(String mail) {
		return userRepository.findByEmail(mail).orElse(null);
	}

	public User getUserByCarRegistration(String registration) {
		return userRepository.findUsersByCarRegistration(registration).orElse(null);
	}

	public List<User> getUserByCarModel(String model) {
		return userRepository.findUsersByCarModel(model);
	}

}
