package com.example.Phase12.commands.team;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class addTeamCommandTeamIdException {

    private int idTeam;

    public addTeamCommandTeamIdException(int idTeam) {
        this.idTeam = idTeam;
    }

}
