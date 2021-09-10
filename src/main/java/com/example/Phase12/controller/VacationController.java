package com.example.Phase12.controller;

import com.example.Phase12.commands.addVacationCommand;
import com.example.Phase12.exceptions.BadArgumentsException;
import com.example.Phase12.exceptions.ResourceNotFoundException;
import com.example.Phase12.repository.VacationRepository;
import com.example.Phase12.sections.Vacation;
import com.example.Phase12.service.VacationService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/VacationController")
public class VacationController {

    private VacationService vacationService;
    private VacationRepository vacationRepository;

    public VacationController(VacationService vacationService, VacationRepository vacationRepository) {
        this.vacationService = vacationService;
        this.vacationRepository = vacationRepository;
    }

    @PostMapping(value = "addVacation",produces= MediaType.APPLICATION_JSON_VALUE)
    public addVacationCommand addVacation(@RequestBody addVacationCommand vacationCommand)throws Exception{
        if(vacationRepository.existsById(vacationCommand.getId()))
            throw new BadArgumentsException("vacation with this id is added before!");

        if(vacationCommand.getEmployee_name() == null)
            throw new ResourceNotFoundException("The name of the employee is null!");

        if(vacationCommand.getYear() == 0)
            throw new ResourceNotFoundException("The year is null!");

        if(vacationCommand.getExceeded_day() == 0)
            throw new ResourceNotFoundException("No value present");

        return vacationService.savingVacation(vacationCommand);
    }

    @GetMapping(value = "get/{id}")
    public Optional<Vacation> getVacation(@PathVariable int id){

        if(!vacationRepository.existsById(id))
            throw new ResourceNotFoundException("vacation with this id doesn't exist!");

        return vacationService.gettingVacation(id);
    }
}
