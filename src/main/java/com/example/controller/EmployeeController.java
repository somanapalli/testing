package com.example.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Employee;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.EmployeeRepository;

@RestController
@CrossOrigin(origins = "*")
//@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeController {

	@Autowired
	private EmployeeRepository repository;

	
	@GetMapping("/employees")
	public List<Employee> getListOfEmployees() {
		return repository.findAll();

	}
	
	
	
	@PostMapping("/employees")
	public Employee createEmployee(@RequestBody Employee employee) {

		return repository.save(employee);
	}

	
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Integer id) {

		Employee employee = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exists with id:" + id));

		return ResponseEntity.ok(employee);
	}

	
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployeeById(@PathVariable Integer id, @RequestBody Employee employeeDetails) {
		Employee employee = repository.findById(id)
				.orElseThrow(
						() -> new ResourceNotFoundException("employee not exists with id: " + id)
						);

		
		employee.setFirstName(employeeDetails.getFirstName());
		employee.setLastName(employeeDetails.getLastName());
		employee.setEmailId(employeeDetails.getEmailId());

		Employee updatedEmployee = repository.save(employee);
		return ResponseEntity.ok(updatedEmployee);

	}
	@CrossOrigin(origins = "*")
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<Map<String,Boolean>> deleteEmployee(@PathVariable Integer id){
		
		 Employee employee = repository.findById(id).orElseThrow(()->new ResourceNotFoundException("employee not exists with id:" + id));
	
		 repository.delete(employee);
		 
		 Map<String,Boolean> response = new HashMap<String, Boolean>();
		 response.put("deleted",Boolean.TRUE);
		 return ResponseEntity.ok(response);
	
	}

	
	 

}
