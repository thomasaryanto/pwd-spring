package com.cimb.tokolapak.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin") // untuk grouping prefix routes
public class HelloWorldController {
	@GetMapping("/hello") // untuk get method
	public String helloWorld() {
		return "Halo Dunia!";
	}
	
	@GetMapping("/hello/{name}") // untuk get method dgn paramater
	public String helloName(@PathVariable String name) { // untuk ambil parameternya
		return "Halo "+ name;
	}
}
