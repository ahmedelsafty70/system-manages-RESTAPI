package com.example.Phase12.commands.salaryDetails;

import com.example.Phase12.sections.Employee;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Optional;

@Getter @Setter @NoArgsConstructor
public class addSalaryDetailsCommandSalaryDetailsIdException {

    private int id;

    public addSalaryDetailsCommandSalaryDetailsIdException(int id) {
        this.id = id;
    }

}
