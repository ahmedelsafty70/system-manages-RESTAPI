package com.example.Phase12.commands.department;

import com.example.Phase12.sections.Employee;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class addDepartmentCommand {

    private int id;
    private String name;

    public addDepartmentCommand(int id, String name) {
        this.id = id;
        this.name = name;
    }

}
