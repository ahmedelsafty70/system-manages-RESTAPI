package com.example.Phase12.service;

import com.example.Phase12.commands.earning.addEarningCommand;
import com.example.Phase12.dto.addEarningDto;
import com.example.Phase12.exceptions.BadArgumentsException;
import com.example.Phase12.repository.EarningRepository;
import com.example.Phase12.repository.EmployeeRepository;
import com.example.Phase12.repository.VacationRepository;
import com.example.Phase12.sections.Earnings;
import com.example.Phase12.sections.Employee;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class EarningService {

    private EmployeeRepository employeeRepository;
    private EarningRepository earningRepository;
    private ModelMapper modelMapper;
    private VacationRepository vacationRepository;

    public EarningService(EmployeeRepository employeeRepository, EarningRepository earningRepository, ModelMapper modelMapper, VacationRepository vacationRepository) {
        this.employeeRepository = employeeRepository;
        this.earningRepository = earningRepository;
        this.modelMapper = modelMapper;
        this.vacationRepository = vacationRepository;
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



        if(earningCommand.getBonus() == 0 && earningCommand.getRaises() == 0)  //Bad Request
            throw new BadArgumentsException("You did not request any changes");

        Earnings earnings = mapTOEarning(earningCommand); //From command->Earning To object->Earning

        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());

        Employee employee = employeeRepository.findById(employeeId).orElse(null); //get Employee From Database
        earnings.setEmployee(employee); // put employee in earning
        earnings.setDate(formatter.format(date)); // put date in earning

        if(earnings.getBonus() != 0)
            employee.setBonus((double) earnings.getBonus());
        if(earnings.getRaises() != 0)
            employee.setRaises((double) earnings.getRaises());

        int counterForExceededDays = vacationRepository.counterForTheExceededDays(employeeId);//calculate Deduction Salary
        float DeductionForExceededDays = (employee.getGrossSalary()/22) * counterForExceededDays;
        earnings.setDeductionOfExceededDay((double)DeductionForExceededDays);

        Employee uselessObject = employeeRepository.save(employee); //save employee
        Earnings earningsToBeChanged = earningRepository.save(earnings); //save earning

        return mapToEarningDto(earningsToBeChanged);
    }

    public List<Earnings> getEarning(int employeeId){
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        return employee.getListOfEarnings();
    }

}
