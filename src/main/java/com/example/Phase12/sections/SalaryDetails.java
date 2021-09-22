package com.example.Phase12.sections;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table

@Getter@Setter@NoArgsConstructor
public class SalaryDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column(name = "actual_salary")
    private Float actualSalary;

    @Column(name = "date")
    private Date date;


    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="employee_id")
    private Employee employee;
}
