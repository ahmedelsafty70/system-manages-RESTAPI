package com.example.Phase12.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class addTeamDto {

    private int idTeam;
    private String teamName;


    public addTeamDto(int idTeam, String teamName) {
        this.idTeam = idTeam;
        this.teamName = teamName;
    }

}
