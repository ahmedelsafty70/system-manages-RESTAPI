package com.example.Phase12.commands.vacation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class addVacationCommandVacationIdException {

    private int id;

    public addVacationCommandVacationIdException(int id) {
        this.id = id;
    }

}
