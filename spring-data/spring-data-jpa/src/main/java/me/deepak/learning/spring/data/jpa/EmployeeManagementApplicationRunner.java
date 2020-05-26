package me.deepak.learning.spring.data.jpa;

import me.deepak.learning.spring.data.jpa.model.Employee;
import me.deepak.learning.spring.data.jpa.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class EmployeeManagementApplicationRunner implements ApplicationRunner {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        initialize();
    }

    private void initialize() {
        initializeDataSource();
    }

    private void initializeDataSource() {
        List<Employee> employees = IntStream.range(1, 200000)
                .boxed()
                .map(i -> new Employee.Builder()
                        .withFirstName("FirstName" + i)
                        .withLastName("LastName" + i)
                        .withEmail("Email" + i + "@marvel.com")
                        .withJoiningDate(LocalDate.now())
                        .build())
                .collect(Collectors.toList());

        employeeRepository.mergeAll(employees);
    }
}
