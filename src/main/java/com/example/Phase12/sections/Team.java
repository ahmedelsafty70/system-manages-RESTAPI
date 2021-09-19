package com.example.Phase12.sections;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name ="team")

@Getter@Setter@NoArgsConstructor
public class Team {

    @Id
    @Column(name = "idTeam")
    private int idTeam;

    @Column(name = "teamName")
    private String teamName;

    @JsonIgnore
    @OneToMany(mappedBy = "team",fetch = FetchType.EAGER)
    private List<Employee> employees;


    public Team(int idTeam, String teamName, List<Employee> employees) {
        this.idTeam = idTeam;
        this.teamName = teamName;
        this.employees = employees;
    }

}
