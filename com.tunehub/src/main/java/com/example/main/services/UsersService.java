package com.example.main.services;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.example.main.dto.UserDTO;
import com.example.main.dto.UserRegisterDTO;

public interface UsersService {
	
    String registerUser(UserRegisterDTO userRegisterDTO);
    UserDTO signin(String email, String password);
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long id);
    UserDTO getUserByUsername(String username);
    Map<Boolean, String> updateUser(Long id, UserDTO userDTO);
    boolean deleteUser(Long id);
    boolean updateUserPhoto(Long id, MultipartFile image);
    boolean removeProfileImage(Long id);
    boolean updatePrimeStatus(String email);
    UserDTO findByEmail(String email);

}
