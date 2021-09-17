package com.example.Phase12.commands.vacation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class addVacationCommandEmployeeNameException {

    private int id;
    private String employee_name;

    public addVacationCommandEmployeeNameException(int id, String employee_name) {
        this.id = id;
        this.employee_name = employee_name;
    }

}
