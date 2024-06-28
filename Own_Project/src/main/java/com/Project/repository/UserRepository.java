package com.Project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Project.entity.User;

public interface UserRepository extends JpaRepository<User,Long> {

	Optional<User> findByEmail(String username);

	boolean existsByEmail(String email);

}
