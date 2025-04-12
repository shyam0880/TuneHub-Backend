package com.example.main.services;

import java.util.Map;

import com.example.main.dto.UserDTO;
import com.razorpay.RazorpayException;

public interface RazorpayService {
	
	Map<String, Object> createOrder(int amount, String email) throws RazorpayException;
	
	String handleWebhook(String payload) throws Exception;
	
	public UserDTO makeUserPremium(String email);

}
