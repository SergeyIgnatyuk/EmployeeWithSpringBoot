package com.rest;

import com.exceptions.ResourceNotFoundException;
import com.model.Department;
import com.model.Employee;
import com.service.DepartmentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integrations tests of {@link DepartmentController}.
 *
 * @author Sergey Ignatyuk
 * @version 1.0
 */

@RunWith(SpringRunner.class)
@WebMvcTest(DepartmentController.class)
public class DepartmentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentService departmentService;

    @Test
    public void givenDepartments_whenGetAllDepartments_thenReturnJson() throws Exception {
        List<Department> departmentList = Stream.of(Department.builder()
                .id(1L)
                .name("department's name")
                .description("department's description")
                .phoneNumber("department's phone number")
                .dateOfFormation(new Date())
                .employees(Stream.of(Employee.builder()
                        .id(1L)
                        .fullName("employee's full name")
                        .dateOfBirth(new Date())
                        .phoneNumber("employee's phone number")
                        .emailAddress("employee's email address")
                        .position("employee's position")
                        .dateOfEmployment(new Date())
                        .build()).collect(Collectors.toSet()))
                .build()).collect(Collectors.toList());

        Department firstDepartmentFromList = departmentList.get(0);

        Employee firstEmployeeFromSetOfEmployees = firstDepartmentFromList.getEmployees().stream().findFirst().get();

        when(departmentService.getAllDepartments()).thenReturn(departmentList);

        this.mockMvc.perform(get("/departments").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(firstDepartmentFromList.getId()))
                .andExpect(jsonPath("$[0].name").value(firstDepartmentFromList.getName()))
                .andExpect(jsonPath("$[0].description").value(firstDepartmentFromList.getDescription()))
                .andExpect(jsonPath("$[0].phoneNumber").value(firstDepartmentFromList.getPhoneNumber()))
                .andExpect(jsonPath("$[0].dateOfFormation").value(new SimpleDateFormat("yyyy-MM-dd")
                        .format(firstDepartmentFromList.getDateOfFormation())))
                .andExpect(jsonPath("$[0].employees[0].id").value(firstEmployeeFromSetOfEmployees.getId()))
                .andExpect(jsonPath("$[0].employees[0].fullName").value(firstEmployeeFromSetOfEmployees.getFullName()))
                .andExpect(jsonPath("$[0].employees[0].dateOfBirth").value(new SimpleDateFormat("yyyy-MM-dd")
                        .format(firstEmployeeFromSetOfEmployees.getDateOfBirth())))
                .andExpect(jsonPath("$[0].employees[0].phoneNumber").value(firstEmployeeFromSetOfEmployees.getPhoneNumber()))
                .andExpect(jsonPath("$[0].employees[0].emailAddress").value(firstEmployeeFromSetOfEmployees.getEmailAddress()))
                .andExpect(jsonPath("$[0].employees[0].position").value(firstEmployeeFromSetOfEmployees.getPosition()))
                .andExpect(jsonPath("$[0].employees[0].dateOfEmployment").value(new SimpleDateFormat("yyyy-MM-dd")
                        .format(firstEmployeeFromSetOfEmployees.getDateOfEmployment())));
    }

    @Test
    public void givenDepartment_whenGetDepartmentById_thenReturnJson() throws Exception {
        Long id = 1L;
        Department department = Department.builder()
                .id(id)
                .name("department's name")
                .description("department's description")
                .phoneNumber("department's phone number")
                .dateOfFormation(new Date())
                .employees(Stream.of(Employee.builder()
                        .id(1L)
                        .fullName("employee's full name")
                        .dateOfBirth(new Date())
                        .phoneNumber("employee's phone number")
                        .emailAddress("employee's email address")
                        .position("employee's position")
                        .dateOfEmployment(new Date())
                        .build()).collect(Collectors.toSet()))
                .build();

        Employee firstEmployeeFromSetOfEmployees = department.getEmployees().stream().findFirst().get();

        when(departmentService.getDepartmentById(id)).thenReturn(department);

        this.mockMvc.perform(get("/departments/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(department.getId()))
                .andExpect(jsonPath("$.name").value(department.getName()))
                .andExpect(jsonPath("$.description").value(department.getDescription()))
                .andExpect(jsonPath("$.phoneNumber").value(department.getPhoneNumber()))
                .andExpect(jsonPath("$.dateOfFormation").value(new SimpleDateFormat("yyyy-MM-dd")
                        .format(department.getDateOfFormation())))
                .andExpect(jsonPath("$.employees[0].id").value(firstEmployeeFromSetOfEmployees.getId()))
                .andExpect(jsonPath("$.employees[0].fullName").value(firstEmployeeFromSetOfEmployees.getFullName()))
                .andExpect(jsonPath("$.employees[0].dateOfBirth").value(new SimpleDateFormat("yyyy-MM-dd")
                        .format(firstEmployeeFromSetOfEmployees.getDateOfBirth())))
                .andExpect(jsonPath("$.employees[0].phoneNumber").value(firstEmployeeFromSetOfEmployees.getPhoneNumber()))
                .andExpect(jsonPath("$.employees[0].emailAddress").value(firstEmployeeFromSetOfEmployees.getEmailAddress()))
                .andExpect(jsonPath("$.employees[0].position").value(firstEmployeeFromSetOfEmployees.getPosition()))
                .andExpect(jsonPath("$.employees[0].dateOfEmployment").value(new SimpleDateFormat("yyyy-MM-dd")
                        .format(firstEmployeeFromSetOfEmployees.getDateOfEmployment())));
    }

    @Test
    public void givenResourceNotFoundException_whenGetDepartmentById_thenReturnJson() throws Exception {
        Long departmentId = 1L;
        when(departmentService.getDepartmentById(departmentId)).thenThrow(new ResourceNotFoundException("Department with ID: " + departmentId + " Not Found!"));

        this.mockMvc.perform(get("/departments/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Resource Not Found"))
                .andExpect(jsonPath("$.errors[0]").value("Department with ID: " + departmentId + " Not Found!"));
    }

    @Test
    public void givenConstraintViolationException_whenGetDepartmentById_thenReturnJson() throws Exception {
        this.mockMvc.perform(get("/departments/0").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("Constraint Violation"))
                .andExpect(jsonPath("$.errors[0]").value("getDepartmentById.departmentId: must be greater than or equal to 1"));
    }
}
