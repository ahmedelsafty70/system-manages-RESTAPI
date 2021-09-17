package com.example.Phase12.commands.vacation;

import com.example.Phase12.sections.Employee;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Getter @Setter @NoArgsConstructor
public class addVacationCommandYearBeforeJoiningException {

    private int id;
    private String employee_name;
    private Integer year;
    private Optional<Employee> employee;


    public addVacationCommandYearBeforeJoiningException(int id, String employee_name, Integer year, Optional<Employee> employee) {
        this.id = id;
        this.employee_name = employee_name;
        this.year = year;
        this.employee = employee;
    }

}
