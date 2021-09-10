package com.example.Phase12.controller;

import com.example.Phase12.commands.addTeamCommand;
import com.example.Phase12.exceptions.BadArgumentsException;
import com.example.Phase12.exceptions.ResourceNotFoundException;
import com.example.Phase12.repository.TeamRepository;
import com.example.Phase12.sections.Employee;
import com.example.Phase12.sections.Team;
import com.example.Phase12.service.TeamService;
import javassist.NotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/teamController")
public class TeamController {


    private TeamService teamService;
    private TeamRepository teamRepository;

    public TeamController(TeamService teamService, TeamRepository teamRepository) {
        this.teamService = teamService;
        this.teamRepository = teamRepository;
    }

    @RequestMapping(value = "/adding")
    public addTeamCommand savingTeam(@RequestBody addTeamCommand teamCommand){

        if(teamRepository.existsById(teamCommand.getIdTeam()))
            throw new BadArgumentsException("team with this id is added before!");
        if(teamCommand.getTeamName() == null)
            throw new ResourceNotFoundException("The name is null!");

        return teamService.savingTeam(teamCommand);
    }

    @RequestMapping(value = "/getTeam")
    public Optional<Team> getDep(){

        try {
            return teamService.getTeam(3);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    @GetMapping(value = "/gettingEmployeesUnderTeam/{id}")
    public List<Employee> getEmployeesInTeam(@PathVariable int id) throws NotFoundException{

        if(!teamRepository.existsById(id))
            throw new ResourceNotFoundException("team with this id is not found!");


        return teamService.EmployeesUnderTeam(id);
    }


}