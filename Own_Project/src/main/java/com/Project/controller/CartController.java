package com.Project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Project.entity.Cart;
import com.Project.entity.Product;
import com.Project.response.ErrorResponse;
import com.Project.service.CartService;

@RestController
@RequestMapping("/api/carts")
public class CartController {
    @Autowired
    private CartService cartService;
    
    @PostMapping("/user/{userId}/add/{productId}")
    public Cart addProductToCart(@PathVariable Long userId, @PathVariable Long productId) {
        return cartService.addProductToCart(userId, productId);
    }
    
    @GetMapping("/{userId}/products")
    public ResponseEntity<List<Product>> getProductsByUserId(@PathVariable Long userId) {
        List<Product> products = cartService.getProductsByUserId(userId);
        return ResponseEntity.ok(products);
    }
    
    @PostMapping("/{userId}/checkout")
    public ResponseEntity<ErrorResponse> checkoutCart(@PathVariable Long userId) throws Exception {
        cartService.checkoutCart(userId);
        return ResponseEntity.ok(new ErrorResponse("Checkout successful and cart cleared"));
    }
    @PostMapping("/{userId}/payment")
    public ResponseEntity<String> processPayment(@PathVariable Long userId, @RequestParam String paymentId) throws Exception {
        cartService.processPayment(userId, paymentId);
        return ResponseEntity.ok("Payment processed and cart cleared");
    }
}
