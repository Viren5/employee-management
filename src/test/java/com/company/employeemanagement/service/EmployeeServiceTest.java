package com.company.employeemanagement.service;

import com.company.employeemanagement.entity.Employee;
import com.company.employeemanagement.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.verify;
import java.util.Arrays;
import java.util.List;
import com.company.employeemanagement.exception.ResourceNotFoundException;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository repository;

    @InjectMocks
    private EmployeeService service;

    @Test
    void getAllEmployees() {

        Employee emp1 = new Employee();
        emp1.setId(1L);
        emp1.setName("John");

        Employee emp2 = new Employee();
        emp2.setId(2L);
        emp2.setName("Alice");

        when(repository.findAll())
                .thenReturn(Arrays.asList(emp1, emp2));

        List<Employee> employees = service.getAllEmployees();

        assertEquals(2, employees.size());
    }
    @Test
    void getEmployeeById() {

        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("John");

        when(repository.findById(1L))
                .thenReturn(java.util.Optional.of(employee));

        Employee result = service.getEmployeeById(1L);

        assertEquals("John", result.getName());
    }
    @Test
    void saveEmployee() {

        Employee employee = new Employee();
        employee.setName("John");

        when(repository.save(employee))
                .thenReturn(employee);

        Employee saved = service.saveEmployee(employee);

        assertEquals("John", saved.getName());
    }
    @Test
    void deleteEmployee() {

        service.deleteEmployee(1L);

        verify(repository).deleteById(1L);
    }
    @Test
    void getEmployeeById_NotFound() {

        when(repository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> service.getEmployeeById(99L)
        );
    }
}
