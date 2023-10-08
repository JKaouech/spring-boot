package com.jika.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jika.spring.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
