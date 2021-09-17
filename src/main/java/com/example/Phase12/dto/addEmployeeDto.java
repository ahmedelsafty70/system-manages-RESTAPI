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
    private String roles;        //
    private String national_id;
    private String username;
    private String password;
    private Integer active;
    private Integer yearsOfExperience;
    private Float grossSalary;
    private Gender gender;
    private DegreeEnum degreeEnum;


    public addEmployeeDto(int idEmployee, String second_name, String roles, String national_id, String username, String password, Integer active, Integer yearsOfExperience, Float grossSalary, Gender gender, DegreeEnum degreeEnum) {
        this.idEmployee = idEmployee;
        this.second_name = second_name;
        this.roles = roles;
        this.national_id = national_id;
        this.username = username;
        this.password = password;
        this.active = active;
        this.grossSalary = grossSalary;
        this.yearsOfExperience = yearsOfExperience;
        this.gender = gender;
        this.degreeEnum = degreeEnum;
    }
}
