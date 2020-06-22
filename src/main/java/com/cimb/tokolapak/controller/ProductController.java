package com.cimb.tokolapak.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cimb.tokolapak.dao.ProductRepo;
import com.cimb.tokolapak.entity.Product;
import com.cimb.tokolapak.service.ProductService;

@RestController
public class ProductController {
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductRepo productRepo;
	
	@GetMapping("/products")
	public Iterable<Product> getProducts(){
		return productService.getProducts();
	}
	
	@GetMapping("/products/{id}") // untuk get method dgn paramater
	public Optional<Product> getProductById(@PathVariable int id) { // untuk ambil parameternya
		return productService.getProductById(id);
	}
	
//	@GetMapping("/productName/{name}") // untuk get method dgn paramater
//	public Product findProductByName(@PathVariable String name) { // untuk ambil parameternya
//		return productRepo.findByProductName(name);
//	}
	
	@PostMapping("/products")
	public Product addProduct(@RequestBody Product product) {
		return productService.addProduct(product);
	}
	
	@PutMapping("/products")
	public Product updateProduct(@RequestBody Product product) {
		return productService.updateProduct(product);
	}
	
	@DeleteMapping("/products/{id}")
	public void deleteProductById(@PathVariable int id) {
		productService.deleteProductById(id);
	}
	
	@GetMapping("/products/custom")
	public Iterable<Product> getProductsCustom(){
		return productRepo.findProductByMinPrice(10000, 25000);
	}
}
