package com.example.Phase12.service;

import com.example.Phase12.commands.team.addTeamCommand;
import com.example.Phase12.dto.addTeamDto;
import com.example.Phase12.repository.TeamRepository;
import com.example.Phase12.sections.Employee;
import com.example.Phase12.sections.Team;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {


    public TeamRepository teamRepository;
    private ModelMapper modelMapper;

    public TeamService(TeamRepository teamRepository, ModelMapper modelMapper) {
        this.teamRepository = teamRepository;
        this.modelMapper = modelMapper;
    }

    public addTeamDto savingTeam(addTeamCommand teamCommand){

        Team team = mapToTeam(teamCommand);

        Team teamToBeChanged = teamRepository.save(team);

        addTeamDto teamDto = new addTeamDto(teamToBeChanged.getIdTeam(), teamToBeChanged.getTeamName());

        return teamDto;
    }

    private Team mapToTeam(addTeamCommand teamCommand){

        Team team = modelMapper.map(teamCommand,Team.class);
        return team;
    }

    private addTeamDto mapToTeamCommand(Team team){
        addTeamDto teamDto = modelMapper.map(team,addTeamDto.class);
        return teamDto;
    }

    public addTeamDto getTeam(int id)
    {
        Team team = teamRepository.findById(id).orElse(null);

        addTeamDto teamDto = mapToTeamCommand(team);

        return teamDto;
    }
    public List<Employee> EmployeesUnderTeam(int id) {
        Optional<Team> team = teamRepository.findById(id);
        return team.get().getEmployees();
    }
}

