package com.example.Phase12.commands;

import com.example.Phase12.sections.Employee;

import java.util.Date;
import java.util.Optional;

public class addSalaryDetailsCommand {

    private int id;
    private Float actualSalary;
    private Date date;
    private Optional<Employee> employee;


    public addSalaryDetailsCommand(int id, Float actualSalary, Date date, Optional<Employee> employee) {
        this.id = id;
        this.actualSalary = actualSalary;
        this.date = date;
        this.employee = employee;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Float getActualSalary() {
        return actualSalary;
    }

    public void setActualSalary(Float actualSalary) {
        this.actualSalary = actualSalary;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Optional<Employee> getEmployee() {
        return employee;
    }

    public void setEmployee(Optional<Employee> employee) {
        this.employee = employee;
    }
}
