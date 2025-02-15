package com.example.main.services;

import java.util.Map;

import com.razorpay.RazorpayException;

public interface RazorpayService {
	
	Map<String, Object> createOrder(int amount) throws RazorpayException;

}
