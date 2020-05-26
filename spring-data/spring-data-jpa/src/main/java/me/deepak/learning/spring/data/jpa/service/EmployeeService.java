package me.deepak.learning.spring.data.jpa.service;

import me.deepak.learning.spring.data.jpa.model.Employee;
import me.deepak.learning.spring.data.jpa.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    public void createEmployee(Employee employee) {
        repository.save(employee);
    }
}