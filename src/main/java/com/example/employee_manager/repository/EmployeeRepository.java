package com.example.employee_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.employee_manager.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // Find employee by email
    boolean existsByEmail(String email);

    // Find employee by full name
    Employee findByFullName(String fullName);

    boolean existsByEmailAndIdNot(String email, Long id);
}
