package com.rest;

import com.exceptions.ResourceNotFoundException;
import com.model.Employee;
import com.service.EmployeeService;
import org.hamcrest.Matchers;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integrations tests of {@link EmployeeController}.
 *
 * @author Sergey Ignatyuk
 * @version 1.0
 */

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Test
    public void givenEmployee_whenGetEmployeeById_thenReturnJson() throws Exception {
        Long id = 1L;
        Employee employee = Employee.builder()
                .id(id)
                .fullName("full name")
                .dateOfBirth(new Date())
                .phoneNumber("phone number")
                .emailAddress("email address")
                .position("position")
                .dateOfEmployment(new Date())
                .build();

        when(employeeService.getEmployeeById(id)).thenReturn(employee);

        this.mockMvc.perform(get("/employees/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(employee.getId().intValue()))
                .andExpect(jsonPath("$.fullName").value(employee.getFullName()))
                .andExpect(jsonPath("$.dateOfBirth").value(new SimpleDateFormat("yyyy-MM-dd")
                        .format(employee.getDateOfBirth())))
                .andExpect(jsonPath("$.phoneNumber").value(employee.getPhoneNumber()))
                .andExpect(jsonPath("$.emailAddress").value(employee.getEmailAddress()))
                .andExpect(jsonPath("$.position").value(employee.getPosition()))
                .andExpect(jsonPath("$.dateOfEmployment").value(new SimpleDateFormat("yyyy-MM-dd")
                        .format(employee.getDateOfEmployment())));
    }

    @Test
    public void givenResourceNotFoundException_whenGetEmployeeById_thenReturnJson() throws Exception {
        Long employeeId = 20L;

        when(employeeService.getEmployeeById(employeeId)).thenThrow(new ResourceNotFoundException("Employee with ID: " + employeeId + " Not Found!"));

        this.mockMvc.perform(get("/employees/20")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message", Matchers.is("Resource Not Found")))
                .andExpect(jsonPath("$.errors[0]", Matchers.is("Employee with ID: 20 Not Found!")));
    }

    @Test
    public void givenConstraintViolationException_whenGetEmployeeById_thenReturnJson() throws Exception {
        this.mockMvc.perform(get("/employees/0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message", Matchers.is("Constraint Violation")))
                .andExpect(jsonPath("$.errors[0]", Matchers.is("getEmployeeById.employeeId: must be greater than or equal to 1")));
    }
}