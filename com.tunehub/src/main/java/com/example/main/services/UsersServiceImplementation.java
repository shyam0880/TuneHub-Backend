package com.example.main.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.main.entity.Users;
import com.example.main.repository.UsersRepositories;

@Service
public class UsersServiceImplementation implements UsersService{
	@Autowired
	UsersRepositories repo;
	
	@Override
	public String addUser(Users user) {
		repo.save(user);
		return "User added Successfully";
	}

}
