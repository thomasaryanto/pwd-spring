package com.cimb.tokolapak.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cimb.tokolapak.dao.DepartmentRepo;
import com.cimb.tokolapak.dao.EmployeeRepo;
import com.cimb.tokolapak.entity.Department;
import com.cimb.tokolapak.service.DepartmentService;

@RestController
@RequestMapping("/department")
@CrossOrigin(origins = "http://localhost:3000")
public class DepartmentController {
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private DepartmentRepo departmentRepo;
	
	@Autowired
	private EmployeeRepo employeeRepo;
	
	@GetMapping
	public Iterable<Department> getDepartments() {
		return departmentService.getDepartments();
	}
	
	@PostMapping
	public Department addDepartment(@RequestBody Department department) {
		return departmentService.addDepartment(department);
	}
	
	@DeleteMapping("{departmentId}")
	public void deleteDepartment(@PathVariable int departmentId) {
		Department findDepartment = departmentRepo.findById(departmentId).get();
		findDepartment.getEmployees().forEach(employee -> {
			employee.setDepartment(null);
			employeeRepo.save(employee);
		});
		
		departmentRepo.deleteById(departmentId);
		
	}
}
