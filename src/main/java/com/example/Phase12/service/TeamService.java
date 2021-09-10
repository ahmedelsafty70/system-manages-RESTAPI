package com.example.Phase12.service;

import com.example.Phase12.commands.addTeamCommand;
import com.example.Phase12.repository.TeamRepository;
import com.example.Phase12.sections.Employee;
import com.example.Phase12.sections.Team;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {


    public TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public addTeamCommand savingTeam(addTeamCommand teamCommand){

        Team team = new Team();
        team.setIdTeam(teamCommand.getIdTeam());
        team.setTeamName(team.getTeamName());

        teamRepository.save(team);
        return teamCommand;
    }

    public Optional<Team> getTeam(int id) throws NotFoundException
    {
        return teamRepository.findById(id);
    }
    public List<Employee> EmployeesUnderTeam(int id) {
        Optional<Team> team = teamRepository.findById(id);
        return team.get().getEmployees();
    }
}

