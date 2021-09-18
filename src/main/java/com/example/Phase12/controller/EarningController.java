package com.example.Phase12.controller;

import com.example.Phase12.commands.earning.addEarningCommand;
import com.example.Phase12.dto.addEarningDto;
import com.example.Phase12.sections.Earnings;
import com.example.Phase12.security.BaseController;
import com.example.Phase12.service.EarningService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/HumanResources/Earning")
public class EarningController extends BaseController{

    private EarningService earningService;

    public EarningController(EarningService earningService) {
        this.earningService = earningService;
    }

    @PostMapping(value = "add/{idEmployee}")
    public addEarningDto addEarnings(@PathVariable int idEmployee, @RequestBody addEarningCommand earnings) throws Exception {


        return earningService.addEarning(earnings,idEmployee);

    }

//    @GetMapping(name = "get/{idEmployee}")
//    public List<Earnings> getListOfEarningForSpecificEmployee(@PathVariable int employeeId){
//
//        return earningService.getEarning(employeeId);
//    }

}
