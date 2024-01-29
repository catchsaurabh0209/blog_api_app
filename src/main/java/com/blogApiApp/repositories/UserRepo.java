package com.blogApiApp.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogApiApp.entities.User;

public interface UserRepo extends JpaRepository<User, Integer>{

	Optional<User> findByEmail(String email);
	
}
