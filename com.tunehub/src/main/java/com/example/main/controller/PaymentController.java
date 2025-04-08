package com.example.main.controller;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.main.services.RazorpayService;
import com.example.main.services.UsersService;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
	
	@Autowired
	private RazorpayService razorpayService;
	
	@Autowired
	UsersService usersService;
	
	@Value("${razorpay.key.id}")  
	private String keyId;

    @GetMapping("/razorpay-key")
    public Map<String, String> getRazorpayKey() {
        Map<String, String> response = new HashMap<>();
        response.put("razorpayKey", keyId); 
        return response;
    }
    
    @PostMapping("/webhook")
    public ResponseEntity<?> handleWebhook(@RequestBody String payload) {
        try {
        	
        	String message = razorpayService.handleWebhook(payload);
            
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Webhook processing failed: " + e.getMessage());
        }
    }
	
    @PostMapping("/create-order")
    public ResponseEntity<?> createOrder(@RequestBody Map<String, Object> request) {
        try {
            int amount = (int) request.get("amount");
            String email = request.get("email").toString();

            Map<String, Object> orderResponse = razorpayService.createOrder(amount, email);

            return ResponseEntity.ok(orderResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Order creation failed: " + e.getMessage());
        }
    }

	
	
	
}
