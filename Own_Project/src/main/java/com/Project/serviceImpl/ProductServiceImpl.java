package com.Project.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Project.entity.Product;
import com.Project.repository.ProductRepository;
import com.Project.service.ProductService;
@Service
public class ProductServiceImpl implements ProductService{

	  @Autowired
	    private ProductRepository productRepository;
	    
	    public Product addProduct(Product product) {
	        return productRepository.save(product);
	    }
	    
	    public List<Product> getAllProducts() {
	        return productRepository.findAll();
	    }
}
