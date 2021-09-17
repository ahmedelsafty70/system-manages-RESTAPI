package com.example.Phase12.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class addDepartmentDto {

    private int id;
    private String name;

    public addDepartmentDto(int id, String name) {
        this.id = id;
        this.name = name;
    }

}
