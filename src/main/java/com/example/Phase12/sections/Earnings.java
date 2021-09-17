package com.example.Phase12.sections;

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
    private Date date;

    @ManyToOne
    @JoinColumn(name="employee_id")
    private Employee employee;

    public Earnings(float bonus,  float raises) {
        this.bonus = bonus;
        this.raises = raises;
    }


}
