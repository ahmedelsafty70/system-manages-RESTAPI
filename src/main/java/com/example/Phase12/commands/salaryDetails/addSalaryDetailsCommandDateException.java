package com.example.Phase12.commands.salaryDetails;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter @Setter @NoArgsConstructor
public class addSalaryDetailsCommandDateException {

    private int id;
    private Float actualSalary;
    private Date date;

    public addSalaryDetailsCommandDateException(int id, Float actualSalary, Date date) {
        this.id = id;
        this.actualSalary = actualSalary;
        this.date = date;
    }

}
