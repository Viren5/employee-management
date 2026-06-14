package com.company.employeemanagement.service;

import com.company.employeemanagement.entity.Employee;
import com.company.employeemanagement.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import com.company.employeemanagement.dto.EmployeeDTO;
import com.company.employeemanagement.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private static final Logger logger =
            LoggerFactory.getLogger(EmployeeService.class);

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees() {
        logger.info("Fetching all employees");
        return employeeRepository.findAll();
    }

    public Employee saveEmployee(Employee employee) {
        logger.info("Saving employee: {}", employee.getName());
        return employeeRepository.save(employee);
    }
    public Employee getEmployeeById(Long id) {
        logger.info("Fetching employee with id {}", id);
        return employeeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found with id " + id));
    }
    public Employee updateEmployee(Long id, Employee employee) {

        logger.info("Updating employee with id {}", id);
        Employee existingEmployee =
                employeeRepository.findById(id).orElse(null);

        if (existingEmployee == null) {
            return null;
        }

        existingEmployee.setName(employee.getName());
        existingEmployee.setEmail(employee.getEmail());
        existingEmployee.setSalary(employee.getSalary());

        return employeeRepository.save(existingEmployee);
    }
    public void deleteEmployee(Long id) {

        logger.info("Deleting employee with id {}", id);

        employeeRepository.deleteById(id);
    }
}