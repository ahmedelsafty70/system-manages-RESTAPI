package com.example.Phase12.controller;

import com.example.Phase12.sections.Employee;
import com.example.Phase12.sections.Team;
import com.example.Phase12.service.TeamService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/teamController")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @RequestMapping(value = "/adding")
    public Team savingTeam(@RequestBody Team team){ return teamService.savingTeam(team);}

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
        return teamService.EmployeesUnderTeam(id);
    }


}