package com.hackathon.app.controller;

import com.hackathon.app.model.Order;
import com.hackathon.app.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/{userId}/place")
    public ResponseEntity<?> placeOrder(
            @PathVariable Long userId,
            @RequestBody Map<String, String> payload) {
        try {
            String paymentMethod = payload.getOrDefault("paymentMethod", "CREDIT_CARD");
            Order order = orderService.placeOrder(userId, paymentMethod);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/{userId}/history")
    public ResponseEntity<List<Order>> getOrderHistory(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.getOrderHistory(userId));
    }
}
