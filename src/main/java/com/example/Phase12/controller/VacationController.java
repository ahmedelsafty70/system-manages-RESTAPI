package com.example.Phase12.controller;

import com.example.Phase12.commands.vacation.addVacationCommand;
import com.example.Phase12.dto.addVacationDto;
import com.example.Phase12.exceptions.BadArgumentsException;
import com.example.Phase12.exceptions.ResourceNotFoundException;
import com.example.Phase12.repository.VacationRepository;
import com.example.Phase12.sections.Vacation;
import com.example.Phase12.service.VacationService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    public addVacationDto addVacation(@RequestBody addVacationCommand vacationCommand)throws Exception{

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy");
        LocalDateTime now = LocalDateTime.now();

        if(vacationRepository.existsById(vacationCommand.getId()))
            throw new BadArgumentsException("vacation with this id is added before!");

        if(vacationCommand.getEmployee_name() == null)
            throw new ResourceNotFoundException("The name of the employee is null!");

        if(vacationCommand.getCurrentYear() > Integer.parseInt(dtf.format(now)))
            throw new ResourceNotFoundException("This year is invalid! This year didn't come yet.");

        if(vacationCommand.getCurrentYear() < vacationCommand.getEmployee().getJoined_year())
             throw new ResourceNotFoundException("This year is invalid! choose a year after you joined our community.");


        return vacationService.savingVacation(vacationCommand);
    }

    @GetMapping(value = "get/{id}")
    public addVacationDto getVacation(@PathVariable int id){

        if(!vacationRepository.existsById(id))
            throw new ResourceNotFoundException("vacation with this id doesn't exist!");

        return vacationService.gettingVacation(id);
    }
}