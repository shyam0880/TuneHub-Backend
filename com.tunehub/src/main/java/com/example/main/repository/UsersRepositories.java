package com.example.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.main.entity.Users;

public interface UsersRepositories extends JpaRepository<Users, Integer>{
	
	public Users findByEmail(String email);
	
	Users findById(long id);

}
