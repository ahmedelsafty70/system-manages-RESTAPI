package com.example.Phase12.commands.earning;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter@Setter@NoArgsConstructor
public class addEarningCommand {

    private Float bonus;
    private Float raises;
    private String date;
    private Integer employeeId;

    public addEarningCommand(Float bonus, Float raises) {
        this.bonus = bonus;
        this.raises = raises;
    }
}
