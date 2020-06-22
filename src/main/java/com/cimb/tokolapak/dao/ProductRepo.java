package com.cimb.tokolapak.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cimb.tokolapak.entity.Product;

public interface ProductRepo extends JpaRepository<Product, Integer> {
	public Product findByProductName(String productName);
	
	@Query(value = "SELECT * FROM Product WHERE price > ?1 and price < ?2", nativeQuery = true)
	public Iterable<Product> findProductByMinPrice(double minPrice, double maxPrice);
	
	@Query(value = "SELECT * FROM Product WHERE price > :minPrice and product_name like %:namaProduk%", nativeQuery = true)
	public Iterable<Product> findProductByMinPrice2(@Param("minPrice") double minPrice, @Param("namaProduk") String productName);
}
