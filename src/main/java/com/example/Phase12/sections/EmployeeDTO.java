package com.example.Phase12.sections;

public class EmployeeDTO {
    private Float netSalary;
    private Float grossSalary;

    public EmployeeDTO() {
    }

    public static EmployeeDTO EmployeeDEOFunc(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();

        employeeDTO.setGrossSalary(employee.getGrossSalary());
        employeeDTO.setNetSalary(employee.getNetSalary());
        return employeeDTO;
    }

    public Float getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(Float netSalary) {
        this.netSalary = netSalary;
    }

    public Float getGrossSalary() {
        return grossSalary;
    }

    public void setGrossSalary(Float grossSalary) {
        this.grossSalary = grossSalary;
    }
}