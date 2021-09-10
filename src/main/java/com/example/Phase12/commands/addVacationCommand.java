package com.example.Phase12.commands;

import com.example.Phase12.sections.Employee;

import java.util.Optional;

public class addVacationCommand {

    private int id;
    private String employee_name;
    private int year;
    private int exceeded_day;
    private Optional<Employee> employee;



    public addVacationCommand(int id, String employee_name, int year, int exceeded_day, Optional<Employee> employee) {
        this.id = id;
        this.employee_name = employee_name;
        this.year = year;
        this.exceeded_day = exceeded_day;
        this.employee = employee;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public Optional<Employee> getEmployee() {
        return employee;
    }

    public void setEmployee(Optional<Employee> employee) {
        this.employee = employee;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getExceeded_day() {
        return exceeded_day;
    }

    public void setExceeded_day(int exceeded_day) {
        this.exceeded_day = exceeded_day;
    }
}
