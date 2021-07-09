package com.repository;

import com.model.Department;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Integrations tests of {@link DepartmentRepository}.
 *
 * @author Sergey Ignatyuk
 * @version 1.0
 */

@RunWith(SpringRunner.class)
@DataJpaTest
public class DepartmentRepositoryTest {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    @Rollback(value = true)
    public void whenFindAll_thenReturnDepartment() {
        List<Department> departmentList = departmentRepository.findAll();

        Department firstDepartmentFromList = departmentList.get(0);

        Assert.assertEquals(3, departmentList.size());
        Assert.assertEquals(1, firstDepartmentFromList.getId().intValue());
        Assert.assertEquals("QA Department", firstDepartmentFromList.getName());
        Assert.assertEquals("QA Department", firstDepartmentFromList.getDescription());
        Assert.assertEquals("55-89-89", firstDepartmentFromList.getPhoneNumber());
        Assert.assertEquals("2015-03-15", new SimpleDateFormat("yyyy-MM-dd")
                .format(firstDepartmentFromList.getDateOfFormation()));
    }

    @Test
    @Rollback(value = true)
    public void whenFindById_thenReturnDepartment() {
        Department department = departmentRepository.findById(1L).get();

        Assert.assertEquals(1, department.getId().intValue());
        Assert.assertEquals("QA Department", department.getName());
        Assert.assertEquals("QA Department", department.getDescription());
        Assert.assertEquals("55-89-89", department.getPhoneNumber());
        Assert.assertEquals("2015-03-15", new SimpleDateFormat("yyyy-MM-dd")
                .format(department.getDateOfFormation()));
    }
}
