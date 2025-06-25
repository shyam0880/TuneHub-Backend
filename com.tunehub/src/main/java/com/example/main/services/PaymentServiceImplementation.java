package com.example.main.services;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentServiceImplementation implements PaymentService {

    @Value("${razorpay.key.id}")
    private String keyId;

    @Value("${razorpay.key.secret}")
    private String keySecret;

    @Autowired
    private UsersService usersService;

    @Override
    public Map<String, Object> createOrder(int amount, String email) throws RazorpayException {
        RazorpayClient client = new RazorpayClient(keyId, keySecret);

        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", amount * 100); // Amount in paise (₹1 = 100 paise)
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", "txn_123456");
        orderRequest.put("payment_capture", 1);

        JSONObject notes = new JSONObject();
        notes.put("email", email);
        orderRequest.put("notes", notes);

        Order order = client.orders.create(orderRequest);

        Map<String, Object> response = new HashMap<>();
        response.put("order_id", order.get("id"));
        response.put("amount", order.get("amount"));
        response.put("currency", order.get("currency"));
        response.put("email", email);
        return response;
    }

    @Override
    public String handleWebhook(String payload) throws Exception {
        JSONObject json = new JSONObject(payload);
        String event = json.getString("event");

        if ("payment.captured".equals(event)) {
            String email = json.getJSONObject("payload")
                    .getJSONObject("payment")
                    .getJSONObject("entity")
                    .getJSONObject("notes")
                    .getString("email");

            boolean update = usersService.updatePrimeStatus(email);

            if (update) {
                return "User upgraded to premium";
            } else {
                return "User not found";
            }
        }
        return "Ignored event";
    }

}
