package com.example.Phase12.commands.vacation;

import com.example.Phase12.sections.Employee;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Getter @Setter @NoArgsConstructor
public class addVacationCommandYearBeforeJoiningException {

    private int id;
    private String employee_name;
    private int currentYear;
    private int employeeId;


    public addVacationCommandYearBeforeJoiningException(int id, String employee_name, int currentYear, int employeeId) {
        this.id = id;
        this.employee_name = employee_name;
        this.currentYear = currentYear;
        this.employeeId = employeeId;
    }

}
