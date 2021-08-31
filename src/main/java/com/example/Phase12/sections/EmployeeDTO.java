package com.example.Phase12.sections;

public class EmployeeDTO {
    private float netSalary;
    private float grossSalary;

    public EmployeeDTO() {
    }

    public static EmployeeDTO EmployeeDEOFunc(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();

        employeeDTO.setGrossSalary(employee.getGrossSalary());
        employeeDTO.setNetSalary(employee.getNetSalary());
        return employeeDTO;
    }

    public float getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(float netSalary) {
        this.netSalary = netSalary;
    }

    public float getGrossSalary() {
        return grossSalary;
    }

    public void setGrossSalary(float grossSalary) {
        this.grossSalary = grossSalary;
    }
}
