package com.example.Phase12.sections;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class SalaryDetails {
    @Id
    private int id;


    @Column(name = "actual_salary")
    private Float actualSalary;

    @Column(name = "date")
    private Date date;


    @ManyToOne
    @JoinColumn(name="employee_id")
    private Employee employee;


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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
