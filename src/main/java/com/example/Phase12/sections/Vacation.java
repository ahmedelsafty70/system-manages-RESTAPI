package com.example.Phase12.sections;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Optional;

@Entity
public class Vacation { // TO CALCULATE HOW MANY DAYS EXCEEDED TO CALCULATE SALARY

    @Id
  //  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "employee_name")
    private String employee_name;

    @Column(name = "year")
    private int year;

    @Column(name = "exceeded_day")
    private int exceeded_day;



    @ManyToOne
    @JoinColumn(name="employee_id")
    private Employee employee;


    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
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

    public int getExceeded_day() {
        return exceeded_day;
    }

    public void setExceeded_day(int exceeded_day) {
        this.exceeded_day = exceeded_day;
    }

    public int getId() {
        return id;
    }
}
