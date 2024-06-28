package com.Project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Project.entity.Product;

public interface ProductRepository extends JpaRepository<Product,Long>{

}
