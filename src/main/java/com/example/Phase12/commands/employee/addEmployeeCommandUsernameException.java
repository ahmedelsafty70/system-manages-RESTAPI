package com.example.Phase12.commands.employee;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class addEmployeeCommandUsernameException {

    private int idEmployee;
    private String second_name;
    private String national_id;
    private String username;
    private Integer active;
    private Float grossSalary;


    public addEmployeeCommandUsernameException(int idEmployee, String second_name, String national_id, String username, int active, Float grossSalary) {
        this.idEmployee = idEmployee;
        this.second_name = second_name;
        this.national_id = national_id;
        this.username = username;
        this.active = active;
        this.grossSalary = grossSalary;
    }
}
