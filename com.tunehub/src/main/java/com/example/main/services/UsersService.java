package com.example.main.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.main.entity.Users;

public interface UsersService {
	
	public String addUser(Users user);
	public boolean emailExists(String email);
	public boolean validateUser(String email, String password);
	public String getRole(String email);
	public Users getUser(String email);
	public void updateUser(Users user);
	public boolean updatePrimeStatus(String email);
	public Users findById(long id);
	public void updateUserPhoto(int id, MultipartFile image);
	public void deleteUser(int id);
	public List<Users> getAllUsers();

}
