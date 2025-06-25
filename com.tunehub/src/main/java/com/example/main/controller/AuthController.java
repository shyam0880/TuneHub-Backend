package com.example.main.controller;

import org.springframework.security.core.Authentication;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

import com.example.main.dto.AuthRequest;
import com.example.main.dto.UserDTO;
import com.example.main.dto.UserRegisterDTO;
import com.example.main.entity.DBPing;
import com.example.main.entity.Users;
import com.example.main.repository.DBPingRepo;
import com.example.main.repository.UsersRepository;
import com.example.main.services.UsersService;
import com.example.main.util.JwtUtil;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Endpoints for user registration, login, logout, and session check")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsersRepository userRepository; 
    
    @Autowired
    private UsersService usersService;
    
	@Autowired
	DBPingRepo dbPingRepo;
    
    @Operation(summary = "Register a new user", description = "Registers a new user with the provided information.")
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegisterDTO userRequest) {
        String message = usersService.registerUser(userRequest);
        if (!message.equals("User registered successfully")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @Operation(summary = "User login", description = "Authenticates user and returns JWT in an HTTP-only cookie.")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
    	String username = authRequest.getUsername();
    	String password = authRequest.getPassword();
        // Authenticate the user
        org.springframework.security.core.Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        // Get full user object
        Users user = userRepository.findByUsername(username).orElseThrow();

        // Generate JWT
        String token = jwtUtil.generateToken(user.getUsername(), user.getEmail());

        // Create HTTP-only cookie
        ResponseCookie cookie = ResponseCookie.from("jwt", token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(3600)
                .sameSite("None")
                .build();

        // Prepare response without password
        UserDTO userDTO = usersService.signin(username, password);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(userDTO);
    }

    @Operation(summary = "Logout user", description = "Invalidates JWT cookie to log out user.")
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        ResponseCookie deleteCookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .path("/")
                .maxAge(0)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, deleteCookie.toString())
                .body("Logged out");
    }

    @Operation(summary = "Get current logged-in username", description = "Returns the username of the authenticated user.")
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .body("User is not authenticated");
        }
        return ResponseEntity.ok("Logged in as: " + authentication.getName());
    }
    
    @Operation(summary = "Ping the server", description = "Returns pong to indicate server is alive")
    @GetMapping("/ping")
	public ResponseEntity<?> ping() {
		Optional<DBPing> dbPing = dbPingRepo.findById("ping");
		if(dbPing.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("I am sleeping");
	    return ResponseEntity.ok(dbPing.get().getValue());
	}
}

