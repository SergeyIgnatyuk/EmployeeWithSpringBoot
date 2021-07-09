package com.service;

import com.model.Department;

import java.util.List;

/**
 * Service interface for {@link com.model.Employee}
 *
 * @author Sergey Ignatyuk
 * @version 1.0
 */

public interface DepartmentService {
    List<Department> getAllDepartments();

    Department getDepartmentById(Long departmentId);
}
