package com.service;

import com.model.Employee;
import com.repository.EmployeeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

/**
 * Integrations tests of {@link EmployeeServiceImpl}.
 *
 * @author Sergey Ignatyuk
 * @version 1.0
 */

@RunWith(SpringRunner.class)
public class EmployeeServiceImplTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private com.service.EmployeeService employeeService;

    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {
        @Bean
        protected EmployeeRepository employeeRepository() {
            return mock(EmployeeRepository.class);
        }

        @Bean
        protected EmployeeService employeeService() {
            return new EmployeeServiceImpl(employeeRepository());
        }
    }

    @Test
    public void whenGetEmployeeById_findByIdShouldBeCalled() {
        when(employeeRepository.findById(1L)).thenReturn(java.util.Optional.of(new Employee()));

        employeeService.getEmployeeById(1L);
        
        verify(employeeRepository, times(1) ).findById(1L);
    }
}