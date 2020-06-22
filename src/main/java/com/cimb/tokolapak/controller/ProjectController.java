package com.cimb.tokolapak.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cimb.tokolapak.dao.ProjectRepo;
import com.cimb.tokolapak.entity.Employee;
import com.cimb.tokolapak.entity.Project;

@RestController
@RequestMapping("/projects")
public class ProjectController {
	@Autowired
	ProjectRepo projectRepo;
	
	@GetMapping
	public Iterable<Project> getProjects() {
		return projectRepo.findAll();
	}
	
	@GetMapping("/{projectId}")
	public Project getProjectById(@PathVariable int projectId) {
		Project findProject =  projectRepo.findById(projectId).get();
		
		if(findProject == null) { //if ini ga harus karena get udah otomatis nge throw
			throw new RuntimeException("Project not found!");
		}
		
		return findProject;
	}
	
	@PostMapping
	public Project addProject(@RequestBody Project project) {
		return projectRepo.save(project);
	}
	
	@GetMapping("/{projectId}/employees")
	public List<Employee> getProjectEmployees(@PathVariable int projectId){
		Project findProject =  projectRepo.findById(projectId).get();
		
		return findProject.getEmployees();
	}
}
