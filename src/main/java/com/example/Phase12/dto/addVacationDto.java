package com.example.Phase12.dto;

import com.example.Phase12.sections.Employee;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Getter @Setter @NoArgsConstructor
public class addVacationDto {

    private int id;
    private String employee_name;
    private Integer currentYear;
    private Employee employee;



    public addVacationDto(int id, String employee_name, Integer currentYear, Employee employee) {
        this.id = id;
        this.employee_name = employee_name;
        this.currentYear = currentYear;
        this.employee = employee;
    }

}
