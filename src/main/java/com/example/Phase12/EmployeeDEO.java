package com.example.Phase12;

public class EmployeeDEO {
    private Float netSalary;
    private Float grossSalary;

    public EmployeeDEO() {
    }

    public static EmployeeDEO EmployeeDEOFunc(Employee employee) {
        EmployeeDEO employeeDEO = new EmployeeDEO();

        employeeDEO.setGrossSalary(employee.getGrossSalary());
        employeeDEO.setNetSalary(employee.getNetSalary());
        return employeeDEO;
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
