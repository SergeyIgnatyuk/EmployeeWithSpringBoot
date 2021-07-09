package com.service;

import com.model.Department;
import com.model.Employee;
import com.repository.DepartmentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

/**
 * Integrations tests of {@link DepartmentServiceImpl}.
 *
 * @author Sergey Ignatyuk
 * @version 1.0
 */

@RunWith(SpringRunner.class)
public class DepartmentServiceImplTest {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private  DepartmentService departmentService;

    @TestConfiguration
    static class DepartmentServiceImplTestContextConfiguration {
        @Bean
        public DepartmentRepository departmentRepository() {
            return mock(DepartmentRepository.class);
        }

        @Bean
        public DepartmentService departmentService() {
            return new DepartmentServiceImpl(departmentRepository());
        }
    }

    @Test
    public void whenGetAllDepartments_findAllShouldBeCalled() {
        departmentService.getAllDepartments();

        verify(departmentRepository, times(1)).findAll();
    }

    @Test
    public void whenGetDepartmentById_findByIdShouldBeCalled() {
        when(departmentRepository.findById(1L)).thenReturn(java.util.Optional.of(new Department()));

        departmentService.getDepartmentById(1L);

        verify(departmentRepository, times(1) ).findById(1L);
    }
}
