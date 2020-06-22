package com.cimb.tokolapak.service;

import com.cimb.tokolapak.entity.Department;

public interface DepartmentService {
	public Iterable<Department> getDepartments();
	public Department addDepartment(Department department);
}
