package com.example.Phase12.commands.team;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class addTeamCommand {

    private int idTeam;
    private String teamName;


    public addTeamCommand(int idTeam, String teamName) {
        this.idTeam = idTeam;
        this.teamName = teamName;
    }

}
