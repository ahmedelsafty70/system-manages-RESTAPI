package com.example.Phase12.commands.employee;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class addEmployeeCommandSecondNameException {

    private int idEmployee;
    private String second_name;


    public addEmployeeCommandSecondNameException(int idEmployee, String second_name) {
        this.idEmployee = idEmployee;
        this.second_name = second_name;
    }
}
