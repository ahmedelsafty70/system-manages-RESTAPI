package com.example.Phase12.commands.vacation;

import com.example.Phase12.sections.Employee;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Getter @Setter @NoArgsConstructor
public class addVacationCommandVacationIdException {

    private int id;

    public addVacationCommandVacationIdException(int id) {
        this.id = id;
    }

}
