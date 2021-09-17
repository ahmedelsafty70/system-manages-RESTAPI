package com.example.Phase12.commands.salaryDetails;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class addSalaryDetailsCommandActualSalaryException {

    private int id;
    private Float actualSalary;

    public addSalaryDetailsCommandActualSalaryException(int id, Float actualSalary) {
        this.id = id;
        this.actualSalary = actualSalary;
    }

}
