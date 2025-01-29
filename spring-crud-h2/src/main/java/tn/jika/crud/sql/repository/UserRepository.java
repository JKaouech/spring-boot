package tn.jika.crud.sql.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tn.jika.crud.sql.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

	List<User> findAll();

    @Query("SELECT u FROM User u JOIN FETCH u.cars")
	List<User> findAllWithCars();
	
	Optional<User> findByEmail(String mail);

    @Query("SELECT u FROM User u JOIN u.cars c WHERE c.registration = :registration")
    Optional<User> findUsersByCarRegistration(String registration);
    

    @Query("SELECT u FROM User u JOIN u.cars c WHERE c.model = :model")
	List<User> findUsersByCarModel(String model);
}
