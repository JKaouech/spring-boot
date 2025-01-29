package tn.jika.crud.sql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.jika.crud.sql.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
}
