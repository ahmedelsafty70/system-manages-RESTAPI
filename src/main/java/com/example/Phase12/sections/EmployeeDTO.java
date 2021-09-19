package com.example.Phase12.sections;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@NoArgsConstructor
public class EmployeeDTO {
    private Float netSalary;
    private Float grossSalary;


    public static EmployeeDTO EmployeeDEOFunc(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();

        employeeDTO.setGrossSalary(employee.getGrossSalary());
        employeeDTO.setNetSalary(employee.getNetSalary());
        return employeeDTO;
    }


}