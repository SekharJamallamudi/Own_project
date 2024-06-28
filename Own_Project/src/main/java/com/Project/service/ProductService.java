package com.Project.service;

import java.util.List;

import com.Project.entity.Product;

public interface ProductService {

	Product addProduct(Product product);

	List<Product> getAllProducts();

}
