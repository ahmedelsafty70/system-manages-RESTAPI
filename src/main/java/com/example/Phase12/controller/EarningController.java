package com.example.Phase12.controller;

import com.example.Phase12.sections.Earnings;
import com.example.Phase12.security.BaseController;
import com.example.Phase12.service.EarningService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/HumanResources/Earning")
public class EarningController extends BaseController{

    private EarningService earningService;

    public EarningController(EarningService earningService) {
        this.earningService = earningService;
    }

    @PostMapping(value = "add/{idEmployee}")
    public Earnings addEarnings(@PathVariable int idEmployee, @RequestBody Earnings earnings) throws Exception {

        return earningService.addEarning(earnings,idEmployee);

    }

}
