package com.example.main.controller;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.main.services.RazorpayService;
import com.razorpay.RazorpayException;



@CrossOrigin(origins = "*")
@RestController
public class PaymentController {
	
	@Autowired
	private RazorpayService razorpayService;

	
	@PostMapping("/create-order")
    public Map<String, Object> createOrder(@RequestParam int amount) throws RazorpayException {
        return razorpayService.createOrder(amount);
    }
	
}
