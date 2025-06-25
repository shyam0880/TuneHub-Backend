package com.example.main.controller;

import com.example.main.dto.UserDTO;
import com.example.main.dto.UserRegisterDTO;
import com.example.main.services.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Controller", description = "Endpoints for user management")
public class UserController {

    @Autowired
    private UsersService usersService;

    @Operation(
            summary = "Fetch all users",
            description = "Get list of all registered users."
    )
    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        List<UserDTO> users = usersService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @Operation(
            summary = "Get user by ID",
            description = "Fetch user details by unique user ID."
    )
    @GetMapping("/id/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        UserDTO userDTO = usersService.getUserById(id);
        if (userDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found");
        }
        return ResponseEntity.ok(userDTO);
    }

    @Operation(
            summary = "Get user by username",
            description = "Fetch user details by unique username."
    )
    @GetMapping("/username/{username}")
    public ResponseEntity<?> getByUsername(@PathVariable String username) {
        UserDTO userDTO = usersService.getUserByUsername(username);
        if (userDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found");
        }
        return ResponseEntity.ok(userDTO);
    }

    @Operation(
            summary = "Update user details",
            description = "Update user profile information by providing updated details."
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserDTO userRequest) {
    	Map<Boolean, String> data = usersService.updateUser(id, userRequest);

        if (data.containsKey(false)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(data.get(false));
        }

        return ResponseEntity.ok(data.get(true));
    }

    @Operation(
            summary = "Delete user",
            description = "Delete user permanently by user ID."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        boolean deleted = usersService.deleteUser(id);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found");
        }
        return ResponseEntity.ok("User deleted successfully");
    }

    @Operation(
            summary = "Update profile photo",
            description = "Upload and update the profile photo for the user."
    )
    @PutMapping("/{id}/update-photo")
    public ResponseEntity<?> updateUserPhoto(@PathVariable Long id, @RequestParam("image") MultipartFile image) {
        boolean updated = usersService.updateUserPhoto(id, image);
        if (!updated) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found");
        }
        return ResponseEntity.ok("Profile photo updated");
    }

    @Operation(
            summary = "Remove profile photo",
            description = "Remove the profile photo of the user."
    )
    @DeleteMapping("/{id}/remove-photo")
    public ResponseEntity<?> removeProfileImage(@PathVariable Long id) {
        boolean removed = usersService.removeProfileImage(id);
        if (!removed) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found");
        }
        return ResponseEntity.ok("Profile photo removed");
    }
}
