package com.cimb.tokolapak.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimb.tokolapak.dao.EmployeeAddressRepo;
import com.cimb.tokolapak.dao.EmployeeRepo;
import com.cimb.tokolapak.entity.Employee;
import com.cimb.tokolapak.entity.EmployeeAddress;
import com.cimb.tokolapak.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {
//	@Autowired
//	private EmployeeRepo employeeRepo;

	@Autowired
	private EmployeeAddressRepo employeeAddressRepo;
	
	@Autowired
	private EmployeeRepo employeeRepo;
	
	@Override
	public void deleteEmployeeAddress(EmployeeAddress employeeAddress) {
		employeeAddress.getEmployee().setEmployeeAddress(null);
		employeeAddress.setEmployee(null);
		
		employeeAddressRepo.delete(employeeAddress);
	}

	@Override
	public Employee addEmployeeAddress(EmployeeAddress employeeAddress, int employeeId) {
		Optional<Employee> findEmployee = employeeRepo.findById(employeeId);
		
		if(findEmployee.toString() == "Optional.empty") {
			throw new RuntimeException("Employee not found!");
		}
		
		employeeAddress.setEmployee(findEmployee.get());
		employeeAddressRepo.save(employeeAddress);
		findEmployee.get().setEmployeeAddress(employeeAddress);
		employeeRepo.save(findEmployee.get());
		return findEmployee.get();
	}

}
