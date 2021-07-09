package com.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * Simple JavaBean domain object that represents a Employee is build with {@link lombok}.
 * There is validation with {@link javax.validation.constraints}.
 *
 * @author Sergey Ignatyuk
 * @version 1.0
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "The database generated employee ID")
    private Long id;

    @Column(name = "full_name")
    @ApiModelProperty(notes = "The employee full name")
    private String fullName;

    @Column(name = "date_of_birth")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @ApiModelProperty(notes = "The employee date of birth")
    private Date dateOfBirth;

    @Column(name = "phone_number")
    @ApiModelProperty(notes = "The employee phone number")
    private String phoneNumber;

    @Column(name = "email_address")
    @ApiModelProperty(notes = "The employee email address")
    private String emailAddress;

    @Column(name = "position")
    @ApiModelProperty(notes = "The employee position")
    private String position;

    @Column(name = "date_of_employment")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @ApiModelProperty(notes = "Date of employment")
    private Date dateOfEmployment;

    @Column(name = "department_id")
    @JsonIgnore
    private Long departmentId;
}
