package com.hackathon.app.controller;

import com.hackathon.app.model.CartItem;
import com.hackathon.app.model.User;
import com.hackathon.app.service.CartService;
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
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getCartItems(@PathVariable Long userId, HttpServletRequest request) {
        if (!isAuthorized(userId, request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", "Access Denied"));
        }
        return ResponseEntity.ok(cartService.getCartItems(userId));
    }

    @PostMapping("/{userId}/add")
    public ResponseEntity<?> addToCart(
            @PathVariable Long userId,
            @RequestBody Map<String, Integer> payload,
            HttpServletRequest request) {
        if (!isAuthorized(userId, request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", "Access Denied"));
        }
        try {
            Long productId = Long.valueOf(payload.get("productId"));
            Integer quantity = payload.get("quantity");
            CartItem item = cartService.addToCart(userId, productId, quantity);
            return ResponseEntity.ok(item);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{userId}/clear")
    public ResponseEntity<?> clearCart(@PathVariable Long userId, HttpServletRequest request) {
        if (!isAuthorized(userId, request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", "Access Denied"));
        }
        cartService.clearCart(userId);
        return ResponseEntity.ok(Map.of("message", "Cart cleared"));
    }

    private boolean isAuthorized(Long userId, HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        String role = (String) request.getAttribute("role");
        Optional<User> currentUser = userService.getUserByEmail(username);
        // Allow Admin or the User themselves
        return currentUser.isPresent() && ("ADMIN".equals(role) || currentUser.get().getUserId().equals(userId));
    }
}
