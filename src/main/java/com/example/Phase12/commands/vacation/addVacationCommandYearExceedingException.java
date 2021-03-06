package com.example.Phase12.commands.vacation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class addVacationCommandYearExceedingException {

    private int id;
    private String employee_name;
    private Integer currentYear;


    public addVacationCommandYearExceedingException(int id, String employee_name, Integer currentYear) {
        this.id = id;
        this.employee_name = employee_name;
        this.currentYear = currentYear;
    }

}
