package com.example.Phase12.controller;

import com.example.Phase12.commands.salaryDetails.addSalaryDetailsCommand;
import com.example.Phase12.dto.addSalaryDetailsDto;
import com.example.Phase12.exceptions.BadArgumentsException;
import com.example.Phase12.exceptions.ResourceNotFoundException;
import com.example.Phase12.repository.SalaryDetailsRepository;
import com.example.Phase12.sections.SalaryDetails;
import com.example.Phase12.service.SalaryDetailsService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public addSalaryDetailsDto addSalaryDetails(@RequestBody addSalaryDetailsCommand salaryDetailsCommand)throws Exception{

        if(salaryDetailsRepository.existsById(salaryDetailsCommand.getId()))
            throw new BadArgumentsException("salary details with this id already exist!");

        if(salaryDetailsCommand.getActualSalary() < 0)
            throw new BadArgumentsException("actual salary cannot be less than zero!");

        if(salaryDetailsCommand.getDate() == null)
            throw new BadArgumentsException("date is null!");

        return salaryDetailsService.savingSalaryDetails(salaryDetailsCommand);
    }

    @GetMapping(value = "getSalaryDetails/{employeeId}")
    public List<addSalaryDetailsDto> getSalaryDetails(@PathVariable int employeeId){

        if(!salaryDetailsRepository.existsById(employeeId))
            throw new ResourceNotFoundException("salary details with this id isn't found!");

        return salaryDetailsService.gettingSalaryDetails(employeeId);
    }


}
