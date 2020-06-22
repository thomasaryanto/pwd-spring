package com.cimb.tokolapak.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cimb.tokolapak.dao.DepartmentRepo;
import com.cimb.tokolapak.dao.EmployeeAddressRepo;
import com.cimb.tokolapak.dao.EmployeeRepo;
import com.cimb.tokolapak.dao.ProjectRepo;
import com.cimb.tokolapak.entity.Department;
import com.cimb.tokolapak.entity.Employee;
import com.cimb.tokolapak.entity.EmployeeAddress;
import com.cimb.tokolapak.entity.Project;
import com.cimb.tokolapak.service.EmployeeService;

@RestController
@RequestMapping("/employee")
@CrossOrigin(origins = "http://localhost:3000")
public class EmployeeController {
	
	
	
	@Autowired
	private EmployeeRepo employeeRepo;
	
	@Autowired
	private DepartmentRepo departmentRepo;
	
	@Autowired
	private ProjectRepo projectRepo;
	
	@Autowired
	private EmployeeAddressRepo employeeAddressRepo;
	
	@Autowired
	private EmployeeService employeeService;
	
	@PostMapping
	public Employee addEmployee(@RequestBody Employee employee) {
		return employeeRepo.save(employee);
	}
	
	@GetMapping
	public Iterable<Employee> getEmployees() {
		return employeeRepo.findAll();
	}
	
	@DeleteMapping("/address/{id}")
	public void deleteEmployeeAddressById(@PathVariable int id) {
		Optional<EmployeeAddress> employeeAddress = employeeAddressRepo.findById(id);
		
		if(employeeAddress.get() == null) {
			throw new RuntimeException("Employee address not found!");
		}
		
		employeeService.deleteEmployeeAddress(employeeAddress.get());
	}
	
	@PostMapping("/addAddress/{employeeId}")
	public Employee addEmployeeAddress(@RequestBody EmployeeAddress employeeAddress, @PathVariable int employeeId) {
		return employeeService.addEmployeeAddress(employeeAddress, employeeId);
	}
	
	@PutMapping("{employeeId}/department/{departmentId}")
	public Employee addEmployeeDepartment(@PathVariable int employeeId, @PathVariable int departmentId) {
		Employee findEmployee = employeeRepo.findById(employeeId).get();
		Department findDepartment = departmentRepo.findById(departmentId).get();
		
		if (findEmployee == null || findDepartment == null) {
			throw new RuntimeException("Employee or Department not found!");
		}
		
		findEmployee.setDepartment(findDepartment);
		return employeeRepo.save(findEmployee);
		
//		return departmentRepo.findById(departmentId).map(department -> {
//			findEmployee.setDepartment(department);
//			return employeeRepo.save(findEmployee);
//		}).orElseThrow(() -> new RuntimeException("Department not found!")
//		);
	}
	
	@PutMapping("{employeeId}/projects/{projectId}")
	public Employee addEmployeeProject(@PathVariable int employeeId, @PathVariable int projectId) {
		Employee findEmployee = employeeRepo.findById(employeeId).get();
		Project findProject = projectRepo.findById(projectId).get();
		
		if(findEmployee.getProjects().contains(findProject)) {
			throw new RuntimeException("Employee already assigned to this project!");
		}
			
		findEmployee.getProjects().add(findProject);
		return employeeRepo.save(findEmployee);
	}
}
