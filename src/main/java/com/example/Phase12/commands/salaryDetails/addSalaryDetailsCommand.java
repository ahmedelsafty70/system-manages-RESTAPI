package com.example.Phase12.commands.salaryDetails;

import com.example.Phase12.sections.Employee;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Optional;

@Getter @Setter @NoArgsConstructor
public class addSalaryDetailsCommand {

    private int id;
    private Float actualSalary;
    private Date date;
    private Employee employee;


    public addSalaryDetailsCommand(int id, Float actualSalary, Date date, Employee employee) {
        this.id = id;
        this.actualSalary = actualSalary;
        this.date = date;
        this.employee = employee;
    }

}
