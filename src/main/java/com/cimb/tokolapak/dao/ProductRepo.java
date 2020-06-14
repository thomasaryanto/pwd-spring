package com.cimb.tokolapak.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cimb.tokolapak.entity.Product;

public interface ProductRepo extends JpaRepository<Product, Integer> {
	public Product findByProductName(String productName);
}
