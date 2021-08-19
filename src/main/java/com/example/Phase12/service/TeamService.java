package com.example.Phase12.service;

import com.example.Phase12.Employee;
import com.example.Phase12.Repository.TeamRepository;
import com.example.Phase12.Team;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {

    @Autowired
    public TeamRepository teamRepository;

    public Team savingTeam(Team team){ return teamRepository.save(team);}

    public Optional<Team> getTeam(int id) throws NotFoundException
    {
        return teamRepository.findById(id);
    }
    public List<Employee> EmployeesUnderTeam(int id) {
        Optional<Team> team = teamRepository.findById(id);
        return team.get().getEmployees();
    }
}

