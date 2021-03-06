package com.example.Phase12.sections;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Getter @Setter @NoArgsConstructor
@Entity
public class Earnings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "bonus")
    private float bonus;

    @Column(name="raises")
    private float raises;

    @Column(name="date")
    private String date;

    @Column(name = "deduction_of_exceeded_day")
    private Double deductionOfExceededDay;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="employee_id")
    private Employee employee;

}
