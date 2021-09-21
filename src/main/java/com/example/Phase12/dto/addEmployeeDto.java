package com.example.Phase12.dto;

import com.example.Phase12.sections.DegreeEnum;
import com.example.Phase12.sections.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter @Setter @NoArgsConstructor
public class addEmployeeDto {

    private int idEmployee;
    private String second_name;
    private String roles;
    private String national_id;
    private String username;
    private String password;
    private Integer active;
    private Integer yearsOfExperience;
    private Float grossSalary;
    private Gender gender;
    private DegreeEnum degreeEnum;

}
