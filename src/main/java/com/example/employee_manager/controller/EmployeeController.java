package com.example.employee_manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.employee_manager.model.Employee;
import com.example.employee_manager.service.EmployeeService;

import jakarta.validation.Valid;

@Controller
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // 1. READ: Display all employees
    @GetMapping("/")
    public String listEmployees(Model model) {
        model.addAttribute("employees",
                employeeService.getAllEmployees());

        return "employees";
    }

    // 2. CREATE: Display add employee form
    @GetMapping("/employees/new")
    public String showAddForm(Model model) {
        model.addAttribute("employee", new Employee());

        return "employee-form";
    }

    // 3. CREATE: Save new employee
    @PostMapping("/employees/save")
    public String saveEmployee(
            @Valid @ModelAttribute("employee") Employee employee,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            return "employee-form";
        }

        employeeService.saveEmployee(employee);

        return "redirect:/";
    }

    // 4. UPDATE: Display employee edit form
    @GetMapping("/employees/edit/{id}")
    public String showEditForm(
            @PathVariable Long id,
            Model model) {

        Employee employee = employeeService
                .getEmployeeById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Invalid employee ID: " + id));

        model.addAttribute("employee", employee);

        return "employee-form";
    }

    // 5. DELETE: Delete employee
    @GetMapping("/employees/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {

        employeeService.deleteEmployee(id);

        return "redirect:/";
    }
}
