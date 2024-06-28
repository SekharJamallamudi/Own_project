package com.Project.service;

import java.util.List;

import com.Project.entity.Cart;
import com.Project.entity.Product;
import com.razorpay.Order;

public interface CartService {

	Cart addProductToCart(Long userId, Long productId);

	List<Product> getProductsByUserId(Long userId);

	Order checkoutCart(Long userId) throws Exception;

	void processPayment(Long userId, String paymentId) throws Exception;

}
