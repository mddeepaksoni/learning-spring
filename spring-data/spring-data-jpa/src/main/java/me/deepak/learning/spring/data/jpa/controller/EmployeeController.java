package me.deepak.learning.spring.data.jpa.controller;

import io.swagger.annotations.SwaggerDefinition;
import me.deepak.learning.spring.data.jpa.model.Employee;
import me.deepak.learning.spring.data.jpa.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employees")
@SwaggerDefinition
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(service.getAllEmployees());
    }

    @PostMapping
    public ResponseEntity<Void> createEmployee(Employee employee) {
        service.createEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}