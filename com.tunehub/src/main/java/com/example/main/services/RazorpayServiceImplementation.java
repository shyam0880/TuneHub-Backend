package com.example.main.services;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class RazorpayServiceImplementation implements RazorpayService {

    @Value("${razorpay.key.id}")
    private String keyId;

    @Value("${razorpay.key.secret}")
    private String keySecret;

    public Map<String, Object> createOrder(int amount) throws RazorpayException {
    	
        System.out.println("Razorpay Key ID: " + keyId);
        System.out.println("Razorpay Secret: " + keySecret);
    	
        RazorpayClient client = new RazorpayClient(keyId, keySecret);

        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", amount * 100); // Amount in paise (₹1 = 100 paise)
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", "txn_123456");
        orderRequest.put("payment_capture", 1);

        Order order = client.orders.create(orderRequest);

        // Extract necessary details
        Map<String, Object> response = new HashMap<>();
        response.put("order_id", order.get("id"));
        response.put("amount", order.get("amount"));
        response.put("currency", order.get("currency"));
        return response;
    }
}
