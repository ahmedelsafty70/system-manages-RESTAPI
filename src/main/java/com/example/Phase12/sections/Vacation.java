package com.example.Phase12.sections;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Optional;

@Entity

@Getter@Setter@NoArgsConstructor
public class Vacation { // TO CALCULATE HOW MANY DAYS EXCEEDED TO CALCULATE SALARY

    @Id
    private int id;

    @Column(name = "employee_name")
    private String employee_name;

    @Column(name = "current_year")
    private int currentYear;

    @Column(name = "exceeded_day")
    private int exceeded_day;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="employee_id")
    private Employee employee;



}
