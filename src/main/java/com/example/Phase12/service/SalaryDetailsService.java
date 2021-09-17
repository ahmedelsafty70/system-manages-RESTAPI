package com.example.Phase12.service;

import com.example.Phase12.ModelMapperGen;
import com.example.Phase12.commands.salaryDetails.addSalaryDetailsCommand;
import com.example.Phase12.dto.addSalaryDetailsDto;
import com.example.Phase12.repository.EmployeeRepository;
import com.example.Phase12.repository.SalaryDetailsRepository;
import com.example.Phase12.repository.VacationRepository;
import com.example.Phase12.sections.ConstantsDeduction;
import com.example.Phase12.sections.Employee;
import com.example.Phase12.sections.SalaryDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class SalaryDetailsService {

    private SalaryDetailsRepository salaryDetailsRepository;
    private EmployeeRepository employeeRepository;
    private EmployeeService employeeService;
    private VacationRepository vacationRepository;
    private ModelMapper modelMapper;
    private AtomicInteger count = new AtomicInteger(0);

    public SalaryDetailsService(SalaryDetailsRepository salaryDetailsRepository, EmployeeRepository employeeRepository, EmployeeService employeeService, VacationRepository vacationRepository, ModelMapper modelMapper) {
        this.salaryDetailsRepository = salaryDetailsRepository;
        this.employeeRepository = employeeRepository;
        this.employeeService = employeeService;
        this.vacationRepository = vacationRepository;
        this.modelMapper = modelMapper;
    }

    private SalaryDetails mapToSalaryDetails(addSalaryDetailsCommand salaryDetailsCommand){
        SalaryDetails salaryDetails = modelMapper.map(salaryDetailsCommand,SalaryDetails.class);

        return salaryDetails;
    }

    public addSalaryDetailsDto savingSalaryDetails(addSalaryDetailsCommand salaryDetailsCommand){ // To Be Continued

        SalaryDetails salaryDetails = mapToSalaryDetails(salaryDetailsCommand);

        SalaryDetails salaryDetailsToBeSaved = salaryDetailsRepository.save(salaryDetails);

        addSalaryDetailsDto salaryDetailsDto = new addSalaryDetailsDto(salaryDetailsToBeSaved.getId(),salaryDetailsToBeSaved.getActualSalary(),salaryDetailsToBeSaved.getDate(),salaryDetailsToBeSaved.getEmployee());

        return salaryDetailsDto;
    }

    public List<addSalaryDetailsDto> gettingSalaryDetails(int id){

        List<SalaryDetails> salaryDetails = salaryDetailsRepository.salaryOfSpecificEmployee(id);
        List<addSalaryDetailsDto> salaryDetailsDto = new ArrayList<>();
        for(int i=0;i<salaryDetails.size();i++)
        {
            salaryDetailsDto.add(new addSalaryDetailsDto());
            ModelMapperGen.getModelMapperSingleton().map(salaryDetails.get(i), salaryDetailsDto.get(i));
        }

        return salaryDetailsDto;
    }



    @Transactional
    @Scheduled(cron="0 0 0 25 * *") //each month
    public void generateSalaryAllEmployees() {
        List<Employee> Employees = employeeRepository.getAllEmployee();

       for (Employee employee : Employees)
       {
           SalaryDetails salaryDetails = new SalaryDetails();

           Date date = Date.valueOf(LocalDate.now());

           salaryDetails.setDate(date);

           String formattedDate = String.format("yyyy/MM", date);

           String[] arrayOfYearsAndMonths = formattedDate.split("/");


           int yearInInteger = Integer.parseInt(arrayOfYearsAndMonths[0]);

           int noOfDaysExceeded = vacationRepository.counterForTheExceededDays(employee.getIdEmployee(),yearInInteger);

           double calculatingNetSalary = employee.getGrossSalary() + employee.getBonus() + employee.getRaises()
                                            - ConstantsDeduction.insurance - (noOfDaysExceeded * (employee.getGrossSalary()/22));

           employee.setGrossSalary((float) (employee.getGrossSalary()+ employee.getRaises()));
           employee.setBonus(0D);
           employee.setRaises(0D);


           salaryDetails.setActualSalary((float)(calculatingNetSalary - (calculatingNetSalary * ConstantsDeduction.taxes)));

           salaryDetails.setEmployee(employee);

           salaryDetailsRepository.save(salaryDetails);
       }
    }
}
