package tn.jika.crud.sql.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tn.jika.crud.sql.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

	
    @Query("SELECT u FROM User u JOIN FETCH u.roles")
	List<User> findAll();
	
	Optional<User> findByEmail(String mail);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = :role")
	List<User> findUsersByRoleName(String role);
}
