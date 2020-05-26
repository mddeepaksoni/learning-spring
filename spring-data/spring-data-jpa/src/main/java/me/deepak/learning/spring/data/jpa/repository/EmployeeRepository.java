package me.deepak.learning.spring.data.jpa.repository;

import me.deepak.learning.spring.data.jpa.model.Employee;
import me.deepak.learning.spring.data.jpa.repository.support.BaseJpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface EmployeeRepository extends BaseJpaRepository<Employee, Long> {

    @Transactional
    @Modifying
    @Query(value = "insert into tbl_employees(first_name, last_name, email) values(:#{#employee.firstName}, :#{#employee.lastName}, :#{#employee.email})", nativeQuery = true)
    void persistEmployee(@Param("employee") Employee employee);
}
