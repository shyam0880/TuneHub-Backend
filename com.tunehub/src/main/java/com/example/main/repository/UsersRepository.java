package com.example.main.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.main.entity.Users;

public interface UsersRepository extends JpaRepository<Users, Long>{
	
	public Users findByEmail(String email);
	
	Optional<Users> findByUsername(String username);

}
