package com.example.Phase12.sections;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name ="team")
public class Team {

    @Id
    @Column(name = "idTeam")
    private int idTeam;

    @Column(name = "teamName")
    private String teamName;

    @JsonIgnore
    @OneToMany(mappedBy = "team",fetch = FetchType.EAGER)
    private List<Employee> employees;

    public Team() {
    }

    public Team(int idTeam, String teamName, List<Employee> employees) {
        this.idTeam = idTeam;
        this.teamName = teamName;
        this.employees = employees;
    }

    public int getIdTeam() {
        return idTeam;
    }

    public void setIdTeam(int idTeam) {
        this.idTeam = idTeam;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
