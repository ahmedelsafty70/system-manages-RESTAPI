package com.example.Phase12.service;

import com.example.Phase12.commands.earning.addEarningCommand;
import com.example.Phase12.dto.addEarningDto;
import com.example.Phase12.exceptions.BadArgumentsException;
import com.example.Phase12.repository.EarningRepository;
import com.example.Phase12.repository.EmployeeRepository;
import com.example.Phase12.sections.Earnings;
import com.example.Phase12.sections.Employee;
import com.example.Phase12.sections.SalaryDetails;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
public class EarningService {

    private EmployeeRepository employeeRepository;
    private EarningRepository earningRepository;
    private ModelMapper modelMapper;

    public EarningService(EmployeeRepository employeeRepository, EarningRepository earningRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.earningRepository = earningRepository;
        this.modelMapper = modelMapper;
    }

    private Earnings mapTOEarning(addEarningCommand earningCommand){
        Earnings earnings = modelMapper.map(earningCommand,Earnings.class);
        return earnings;
    }

    private addEarningDto mapToEarningDto(Earnings earnings){

        addEarningDto earningDto = modelMapper.map(earnings,addEarningDto.class);
        return earningDto;

    }

    public addEarningDto addEarning(addEarningCommand earningCommand, int employeeId) throws Exception{


        if(earningCommand.getBonus() == 0 && earningCommand.getRaises() == 0)
            throw new BadArgumentsException("You did not request any changes");

            Earnings earnings = mapTOEarning(earningCommand);

        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());

        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        earnings.setEmployee(employee);
        earnings.setDate(formatter.format(date));

        if(earnings.getBonus() != 0)
            employee.setBonus((double) earnings.getBonus());
        if(earnings.getRaises() != 0)
            employee.setRaises((double) earnings.getRaises());

        Employee uselessObject = employeeRepository.save(employee);
        Earnings earningsToBeChanged = earningRepository.save(earnings);

        return mapToEarningDto(earningsToBeChanged);
    }

//    public List<Earnings> getEarning(int employeeId){
//        Employee employee = employeeRepository.findById(employeeId).orElse(null);
//        return employee.getListOfEarnings();
//
//    }

}
