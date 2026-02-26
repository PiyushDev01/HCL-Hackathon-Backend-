package com.hackathon.app.controller;

import com.hackathon.app.model.Order;
import com.hackathon.app.model.User;
import com.hackathon.app.service.OrderService;
import com.hackathon.app.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllOrders(HttpServletRequest request) {
        String role = (String) request.getAttribute("role");
        if (!"ADMIN".equals(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", "Access Denied"));
        }
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @PostMapping("/{userId}/place")
    public ResponseEntity<?> placeOrder(
            @PathVariable Long userId,
            @RequestBody Map<String, String> payload,
            HttpServletRequest request) {
        
        String username = (String) request.getAttribute("username");
        String role = (String) request.getAttribute("role");
        
        Optional<User> currentUser = userService.getUserByEmail(username);
        
        // Allow if user is ADMIN or if the userId matches the logged-in user
        if (currentUser.isEmpty() || (!"ADMIN".equals(role) && !currentUser.get().getUserId().equals(userId))) {
             return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", "Access Denied"));
        }
        
        try {
            String paymentMethod = payload.getOrDefault("paymentMethod", "CREDIT_CARD");
            Order order = orderService.placeOrder(userId, paymentMethod);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/{userId}/history")
    public ResponseEntity<?> getOrderHistory(@PathVariable Long userId, HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        String role = (String) request.getAttribute("role");
        
        Optional<User> currentUser = userService.getUserByEmail(username);
        if (currentUser.isEmpty() || (!"ADMIN".equals(role) && !currentUser.get().getUserId().equals(userId))) {
             return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", "Access Denied"));
        }e Long userId, HttpServletRequest request) {
        // Ensure user can only see their own history
        // String username = (String) request.getAttribute("username");
        // TODO: Validate that username belongs to userId
        
        return ResponseEntity.ok(orderService.getOrderHistory(userId));
    }
}
