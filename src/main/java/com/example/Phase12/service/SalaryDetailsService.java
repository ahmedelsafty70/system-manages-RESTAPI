package com.example.Phase12.service;

import com.example.Phase12.commands.addSalaryDetailsCommand;
import com.example.Phase12.repository.EmployeeRepository;
import com.example.Phase12.repository.SalaryDetailsRepository;
import com.example.Phase12.repository.VacationRepository;
import com.example.Phase12.sections.ConstantsDeduction;
import com.example.Phase12.sections.Employee;
import com.example.Phase12.sections.SalaryDetails;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SalaryDetailsService {

    private SalaryDetailsRepository salaryDetailsRepository;
    private EmployeeRepository employeeRepository;
    private EmployeeService employeeService;
    private VacationRepository vacationRepository;

    public SalaryDetailsService(SalaryDetailsRepository salaryDetailsRepository, EmployeeRepository employeeRepository, EmployeeService employeeService) {
        this.salaryDetailsRepository = salaryDetailsRepository;
        this.employeeRepository = employeeRepository;
        this.employeeService = employeeService;
    }

    public addSalaryDetailsCommand savingSalaryDetails(addSalaryDetailsCommand salaryDetailsCommand){ // To Be Continued

        SalaryDetails salaryDetails = new SalaryDetails();

        salaryDetails.setActualSalary(salaryDetailsCommand.getActualSalary());
        salaryDetails.setId(salaryDetailsCommand.getId());
        salaryDetails.setDate(salaryDetailsCommand.getDate());
        salaryDetails.setEmployee(salaryDetailsCommand.getEmployee().get());

        salaryDetailsRepository.save(salaryDetails);

        return salaryDetailsCommand;
    }

    public Optional<SalaryDetails> gettingSalaryDetails(int id){
        return salaryDetailsRepository.findById(id);
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
                                            - ConstantsDeduction.insurance - (yearInInteger * (employee.getGrossSalary()/22));

           employee.setBonus(0D);
           employee.setRaises(0D);

           calculatingNetSalary = calculatingNetSalary - (calculatingNetSalary * ConstantsDeduction.taxes);

           salaryDetails.setActualSalary((float)calculatingNetSalary);

           salaryDetails.setEmployee(employee);
       }


    }
}
