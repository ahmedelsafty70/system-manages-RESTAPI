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

        return vacationService.savingVacation(vacationCommand);
    }

    @GetMapping(value = "get/{id}")
    public addVacationDto getVacation(@PathVariable int id){


        return vacationService.gettingVacation(id);
    }
}