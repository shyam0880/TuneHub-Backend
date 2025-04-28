package com.example.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.main.dto.SongDTO;
import com.example.main.entity.Song;
import com.example.main.entity.Users;
import com.example.main.services.SongService;
import com.example.main.services.UsersService;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class UserController {
	@Autowired
	UsersService usersService;
	
	@Autowired
	SongService songService;
	
	@PostMapping("/register")
	public ResponseEntity<?> addUsers(@RequestBody Users user)
	{
		if(usersService.emailExists(user.getEmail())){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Email is already registered");
        }else {
            String message = usersService.addUser(user);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(message);
        }
		
	}
	@PostMapping("/signin")
	public ResponseEntity<?> signin(@RequestBody Users user) {
		String email = user.getEmail();
		String password = user.getPassword();
		if(usersService.emailExists(email)) {
			if(usersService.validateUser(email, password)==true) {				
				String role = usersService.getRole(email);
				return ResponseEntity.ok(usersService.getUser(email));
			}
			else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login fails");
			}
			
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Exist");
		}
	}
	
	@PutMapping("/user/{id}/update-photo")
	public ResponseEntity<?> updateUserPhoto(@PathVariable int id, @RequestParam("image") MultipartFile image) {
	    try {
	        String imageUrl = usersService.updateUserPhoto(id, image);
	        return ResponseEntity.ok(imageUrl);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("Error updating image: " + e.getMessage());
	    }
	}
	
	 @DeleteMapping("/{id}/remove-image")
	 public ResponseEntity<String> removeProfileImage(@PathVariable Long id) {
	        usersService.removeProfileImage(id);
	        return ResponseEntity.ok("Image removed successfully.");
	 }
	
	@GetMapping("/users")
	public ResponseEntity<List<Users>> getAllUsers() {
	    return ResponseEntity.ok(usersService.getAllUsers());
	}
	
	@GetMapping("/user/{id}")
	public ResponseEntity<?> getUserById(@PathVariable int id) {
	    Users user = usersService.findById(id);
	    if (user != null) {
	        return ResponseEntity.ok(user);
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
	    }
	}
	
	@PutMapping("/user/{id}")
	public ResponseEntity<?> updateUser(@PathVariable int id, @RequestBody Users updatedUser) {
	    Users existingUser = usersService.findById(id);
	    if (existingUser != null) {
	        existingUser.setUsername(updatedUser.getUsername());
	        existingUser.setAddress(updatedUser.getAddress());
	        existingUser.setGender(updatedUser.getGender());
	        // Don't update email/password/role here unless intended
	        usersService.updateUser(existingUser);
	        return ResponseEntity.ok("User updated successfully");
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
	    }
	}
	
	@DeleteMapping("/user/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable int id) {
	    Users user = usersService.findById(id);
	    if (user != null) {
	        usersService.deleteUser(id);
	        return ResponseEntity.ok("User deleted successfully");
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
	    }
	}







}
