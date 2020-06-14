package com.cimb.tokolapak.service.impl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimb.tokolapak.dao.ProductRepo;
import com.cimb.tokolapak.entity.Product;
import com.cimb.tokolapak.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductRepo productRepo;
	
	@Override
	@Transactional
	public Iterable<Product> getProducts() {
		// TODO Auto-generated method stub
		return productRepo.findAll();
	}

	@Override
	@Transactional
	public Optional<Product> getProductById(int id) {
		// TODO Auto-generated method stub
		return productRepo.findById(id);
	}

	@Override
	@Transactional
	public Product addProduct(Product product) {
		// TODO Auto-generated method stub
		product.setId(0);
		return productRepo.save(product);
	}

	@Override
	@Transactional
	public Product updateProduct(Product product) {
		// TODO Auto-generated method stub
		Optional<Product> findProduct = productRepo.findById(product.getId());
		
		if(findProduct.toString() == "Optional.empty") {
			throw new RuntimeException("Product not found!");
		}
		return productRepo.save(product);
	}

	@Override
	@Transactional
	public void deleteProductById(int id) {
		// TODO Auto-generated method stub
		Optional<Product> findProduct = productRepo.findById(id);
		
		if(findProduct.toString() == "Optional.empty") {
			throw new RuntimeException("Product not found!");
		}
		
		productRepo.deleteById(id);
	}

}
