package com.example.Phase12.controller;

import com.example.Phase12.commands.addSalaryDetailsCommand;
import com.example.Phase12.exceptions.BadArgumentsException;
import com.example.Phase12.exceptions.ResourceNotFoundException;
import com.example.Phase12.repository.SalaryDetailsRepository;
import com.example.Phase12.sections.SalaryDetails;
import com.example.Phase12.service.SalaryDetailsService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/salaryController")
public class SalaryDetailsController {

    private SalaryDetailsService salaryDetailsService;
    private SalaryDetailsRepository salaryDetailsRepository;

    public SalaryDetailsController(SalaryDetailsService salaryDetailsService, SalaryDetailsRepository salaryDetailsRepository) {
        this.salaryDetailsService = salaryDetailsService;
        this.salaryDetailsRepository = salaryDetailsRepository;
    }

    @PostMapping(value = "addSalaryDetails",produces= MediaType.APPLICATION_JSON_VALUE)
    public addSalaryDetailsCommand addSalaryDetails(@RequestBody addSalaryDetailsCommand salaryDetailsCommand)throws Exception{

        if(salaryDetailsRepository.existsById(salaryDetailsCommand.getId()))
            throw new BadArgumentsException("salary details with this id already exist!");

        if(salaryDetailsCommand.getActualSalary() == 0)
            throw new BadArgumentsException("salary details with this id already exist!");

        if(salaryDetailsCommand.getDate() == null)
            throw new BadArgumentsException("date of salary details already exist!");

        return salaryDetailsService.savingSalaryDetails(salaryDetailsCommand);
    }

    @GetMapping(value = "getSalaryDetails/{id}")
    public Optional<SalaryDetails> getSalaryDetails(@PathVariable int id){

        if(!salaryDetailsRepository.existsById(id))
            throw new ResourceNotFoundException("salary details with this id isn't found!");

        return salaryDetailsService.gettingSalaryDetails(id);
    }


}
