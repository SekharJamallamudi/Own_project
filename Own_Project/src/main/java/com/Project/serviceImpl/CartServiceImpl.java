package com.Project.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Project.entity.Cart;
import com.Project.entity.Product;
import com.Project.entity.User;
import com.Project.repository.CartRepository;
import com.Project.repository.ProductRepository;
import com.Project.repository.UserRepository;
import com.Project.service.CartService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
@Service
public class CartServiceImpl implements CartService {
	 @Autowired
	    private CartRepository cartRepository;
	    @Autowired
	    private UserRepository userRepository;
	    @Autowired
	    private  RazorpayClient razorpayClient;
	  
	  
	    private ProductRepository productRepository;
	    public Cart addProductToCart(Long userId, Long productId) {
	        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
	        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

	        Cart cart = cartRepository.findByUserId(userId).orElse(new Cart());
	        if (cart.getId() == null) {
	            cart.setUser(user);
	        }

	        cart.getProducts().add(product);
	        return cartRepository.save(cart);
	    }
	    
	    @Override
	    public List<Product> getProductsByUserId(Long userId) {
	        if (userId == null) {
	            throw new IllegalArgumentException("User ID cannot be null");
	        }

	        Optional<Cart> cartOptional = cartRepository.findByUserId(userId);
	        if (cartOptional.isPresent()) {
	            return cartOptional.get().getProducts();
	        } else {
	            throw new IllegalArgumentException("Cart not found for user ID: " + userId);
	        }
	    }
	    
	    @Override
	    public Order checkoutCart(Long userId) throws Exception {
	        if (userId == null) {
	            throw new IllegalArgumentException("User ID cannot be null");
	        }

	        Optional<Cart> cartOptional = cartRepository.findByUserId(userId);
	        if (cartOptional.isPresent()) {
	            Cart cart = cartOptional.get();
	            double totalAmount = cart.getProducts().stream().mapToDouble(Product::getPrice).sum();
	            int amountInPaise = (int) (totalAmount * 100);

	            JSONObject orderRequest = new JSONObject();
	            orderRequest.put("amount", amountInPaise);
	            orderRequest.put("currency", "INR");
	            orderRequest.put("receipt", "receipt_" + userId);

	            return razorpayClient.orders.create(orderRequest);
	        } else {
	            throw new IllegalArgumentException("Cart not found for user ID: " + userId);
	        }
	    }

	    @Override
	    public void processPayment(Long userId, String paymentId) throws Exception {
	        if (userId == null || paymentId == null) {
	            throw new IllegalArgumentException("User ID and Payment ID cannot be null");
	        }

	        Optional<Cart> cartOptional = cartRepository.findByUserId(userId);
	        if (cartOptional.isPresent()) {
	            Cart cart = cartOptional.get();
	            // Verify payment with Razorpay here if needed

	            cart.getProducts().clear();
	            cartRepository.save(cart);
	        } else {
	            throw new IllegalArgumentException("Cart not found for user ID: " + userId);
	        }
	    }
	}
	