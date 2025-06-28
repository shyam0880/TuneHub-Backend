package com.example.main.controller;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.main.services.PaymentService;
import com.example.main.services.UsersService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/payment")
@Tag(name = "Payment Integration", description = "Endpoints for handling Razorpay payments and premium access")
public class PaymentController {
	
	@Autowired
	private PaymentService paymentServices;
	
	@Autowired
	UsersService usersService;
	
	@Value("${razorpay.key.id}")  
	private String keyId;

	@Operation(summary = "Get Razorpay Key", description = "Returns the Razorpay public key for frontend usage")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved Razorpay key")
    @GetMapping("/razorpay-key")
    public Map<String, String> getRazorpayKey() {
        Map<String, String> response = new HashMap<>();
        response.put("razorpayKey", keyId); 
        return response;
    }
    
	@Operation(summary = "Handle Razorpay Webhook", description = "Processes the webhook sent by Razorpay after payment status update")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Webhook processed successfully"),
        @ApiResponse(responseCode = "500", description = "Webhook processing failed")
    })
    @PostMapping("/webhook")
    public ResponseEntity<?> handleWebhook(@RequestBody String payload) {
        try {
        	
        	String message = paymentServices.handleWebhook(payload);
            
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Webhook processing failed: " + e.getMessage());
        }
    }
	
	@Operation(summary = "Create Razorpay Order", description = "Initiates a new Razorpay order with the provided amount and user email")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Order created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request or order creation failed")
    })
    @PostMapping("/create-order")
    public ResponseEntity<?> createOrder(@RequestBody Map<String, Object> request) {
        try {
            int amount = (int) request.get("amount");
            String email = request.get("email").toString();

            Map<String, Object> orderResponse = paymentServices.createOrder(amount, email);

            return ResponseEntity.ok(orderResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Order creation failed: " + e.getMessage());
        }
    }
    
	@Operation(summary = "Make User Premium (Manual)", description = "Temporarily marks a user as premium manually by email. Used if payment is not verified via webhook.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User premium status updated")
    })
    @PutMapping("/make-premium")
    public ResponseEntity<String> makeUserPremium(@RequestBody Map<String, String> payload) {
    	String email = payload.get("email");
    	boolean paymentStatus = usersService.updatePrimeStatus(email);
        if(paymentStatus) return ResponseEntity.ok("Payment Successfull, Enjoy the Music");
        else return ResponseEntity.ok("Sorry, Payment unsuccessfull");
    }	
}
