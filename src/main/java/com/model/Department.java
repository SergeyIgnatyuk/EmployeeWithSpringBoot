package com.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Simple JavaBean domain object that represents a Department is build with {@link lombok}.
 * There is validation with {@link javax.validation.constraints}.
 *
 * @author Sergey Ignatyuk
 * @version 1.0
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "The database generated department ID")
    private Long id;

    @Column(name = "name")
    @ApiModelProperty(notes = "The department's name")
    private String name;

    @Column(name = "description")
    @ApiModelProperty(notes = "The department's description")
    private String description;

    @Column(name = "phone_number")
    @ApiModelProperty(notes = "The department's phone number")
    private String phoneNumber;

    @Column(name = "date_of_formation")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @ApiModelProperty(notes = "The department's date of formation")
    private Date dateOfFormation;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    @ApiModelProperty(notes = "Employees which belong to this department")
    private Set<Employee> employees = new HashSet<>();

    @PreRemove
    private void deleteDepartmentFromEmployee() {
        employees.forEach(employee -> employee.setDepartmentId(null));
    }
}
