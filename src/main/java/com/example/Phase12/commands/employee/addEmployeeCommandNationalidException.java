package com.example.Phase12.commands.employee;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class addEmployeeCommandNationalidException {

    private int idEmployee;
    private String national_id;
    private String second_name;


    public addEmployeeCommandNationalidException(int idEmployee, String national_id,String second_name) {
        this.idEmployee = idEmployee;
        this.national_id = national_id;
        this.second_name = second_name;
    }
}
