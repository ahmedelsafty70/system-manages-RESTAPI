package com.example.Phase12.commands.employee;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class addEmployeeCommandRolesException {

    private int idEmployee;
    private String second_name;
    private String national_id;
    private String username;
    private Integer active;
    private Float grossSalary;
    private String password;
    private String yearsOfExperience;
    private String role;


    public addEmployeeCommandRolesException(int idEmployee, String second_name, String national_id, String username, int active, Float grossSalary, String password, String yearsOfExperience, String role) {
        this.idEmployee = idEmployee;
        this.second_name = second_name;
        this.national_id = national_id;
        this.username = username;
        this.active = active;
        this.grossSalary = grossSalary;
        this.password = password;
        this.yearsOfExperience = yearsOfExperience;
        this.role = role;
    }
}
