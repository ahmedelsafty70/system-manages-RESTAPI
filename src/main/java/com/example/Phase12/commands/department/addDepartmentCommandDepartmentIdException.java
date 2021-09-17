package com.example.Phase12.commands.department;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class addDepartmentCommandDepartmentIdException {

    private int id;

    public addDepartmentCommandDepartmentIdException(int id) {
        this.id = id;

    }

}
