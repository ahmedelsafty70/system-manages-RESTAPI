package com.example.Phase12;

public class EmployeeDEO {
    private float netSalary;
    private float grossSalary;

    public EmployeeDEO() {
    }

    public static EmployeeDEO EmployeeDEOFunc(Employee employee) {
        EmployeeDEO employeeDEO = new EmployeeDEO();

        employeeDEO.setGrossSalary(employee.getGrossSalary());
        employeeDEO.setNetSalary(employee.getNetSalary());
        return employeeDEO;
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
