package com.example.Phase12.commands.vacation;

import com.example.Phase12.sections.Employee;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;
@Getter @Setter @NoArgsConstructor
public class addVacationCommand {

    private int id;
    private String employee_name;
    private int currentYear;
    private Employee employee;



    public addVacationCommand(int id, String employee_name, int currentYear, Employee employee) {
        this.id = id;
        this.employee_name = employee_name;
        this.currentYear = currentYear;
        this.employee = employee;
    }

}
