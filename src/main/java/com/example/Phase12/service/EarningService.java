package com.example.Phase12.service;

import com.example.Phase12.repository.EarningRepository;
import com.example.Phase12.repository.EmployeeRepository;
import com.example.Phase12.sections.Earnings;
import com.example.Phase12.sections.Employee;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class EarningService {

    private EmployeeRepository employeeRepository;
    private EarningRepository earningRepository;

    public EarningService(EmployeeRepository employeeRepository, EarningRepository earningRepository) {
        this.employeeRepository = employeeRepository;
        this.earningRepository = earningRepository;
    }

    public Earnings addEarning(Earnings earnings, int employeeId) throws Exception{

        Calendar calendar = Calendar.getInstance();
        Date date = new Date(calendar.getTimeInMillis());

        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        earnings.setEmployee(employee);

        if(earnings.getBonus() != 0)
            employee.setBonus((double) earnings.getBonus());
        if(earnings.getRaises() != 0)
            employee.setRaises((double) earnings.getRaises());

        Employee uselessObject = employeeRepository.save(employee);
        return earningRepository.save(earnings);
    }

}
