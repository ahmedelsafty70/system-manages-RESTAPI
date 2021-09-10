package com.example.Phase12.service;

import com.example.Phase12.commands.addEmployeeCommand;
import com.example.Phase12.repository.EmployeeRepository;
import com.example.Phase12.sections.ConstantsDeduction;
import com.example.Phase12.sections.Employee;
import com.example.Phase12.sections.EmployeeDTO;
import javassist.NotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.lang.Integer.parseInt;

@Service
public class EmployeeService{


    public EmployeeRepository employeeRepository;
    private PasswordEncoder passwordEncoder;

    public EmployeeService(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public addEmployeeCommand savingEmployee(addEmployeeCommand addEmployeeCommand) throws Exception {
        if(employeeRepository.existsById(addEmployeeCommand.getIdEmployee()))
        {
            throw new Exception("employee already exists !");
        }

        Employee employee = new Employee();

        employee.setIdEmployee(addEmployeeCommand.getIdEmployee());
        employee.setSecond_name(addEmployeeCommand.getSecond_name());
        employee.setRoles(addEmployeeCommand.getRoles());
        employee.setNational_id(addEmployeeCommand.getNational_id());
        employee.setUsername(addEmployeeCommand.getUsername());
       // employee.setPassword(addEmployeeCommand.getPassword());
        employee.setActive(addEmployeeCommand.getActive());
       // employee.setYearsOfExperience(addEmployeeCommand.getYearsOfExperience());
        employee.setGrossSalary(addEmployeeCommand.getGrossSalary());


        String passwordEncoded = passwordEncoder.encode(addEmployeeCommand.getPassword());
        employee.setPassword(passwordEncoded);
        Employee modified_employee = calculateNetSalary(employee);



        employeeRepository.save(modified_employee);

        return addEmployeeCommand;
    }

    public Employee calculateNetSalary(Employee employee){


        double net_salary = employee.getGrossSalary() - ConstantsDeduction.insurance ;  // No Bonus And Raises In The First Time

        net_salary = net_salary - (net_salary * ConstantsDeduction.taxes); // No deduction for absence
        employee.setNetSalary((float)net_salary);

        return employee;
    }

    public Optional<Employee> getUser (Integer id) throws NotFoundException
    {
        return employeeRepository.findById(id);
    }

    public addEmployeeCommand modifyUser(addEmployeeCommand employeeModifiedData , Integer id) throws Exception {

        Employee finalModifiedOnEmployee;
        Optional<Employee> empToModify = getUser(id);
        Optional<Employee> employeeWantedToBeChange = getUser(empToModify.get().getIdEmployee());
        if(employeeWantedToBeChange == null)
            return null;
        else  {

        //  finalModifiedOnEmployee = new Employee(employeeModifiedData,empToModify);    ASK AMIN
          savingEmployee(employeeModifiedData);}

        return employeeModifiedData;
    }


    public void deleteEmployee(int id){
        employeeRepository.deleteById(id);
    }


    public float deductGrossSalary(double grossSalary) {
        double netSalary = grossSalary - (grossSalary * 0.15) - 500;
        float netSalaryFloat = (float)netSalary;
        return netSalaryFloat;
    }
//    public double addBonusAndRaises(Employee employee){
//        double grossSalary=0.0;
//
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
//        Date date = new Date();
//
//        String[] arrayForCurrentDate = formatter.format(date).split("/");
//        String[] arrayForDateOfEmployee = employee.getListOfSalaryHistories().get(employee.getIdEmployee()).getDate().toString().split("/");
//
//
//        if(arrayForDateOfEmployee[0].equals(arrayForCurrentDate[0]) && arrayForDateOfEmployee[1].equals(arrayForCurrentDate[1])) {
//            grossSalary = grossSalary + employee.getListOfSalaryHistories().get(employee.getIdEmployee()).getBonus();
//        }
//
//        return grossSalary;
//    }
    public EmployeeDTO gettingSalaries(int id) throws NotFoundException {

        
        Employee employee = getUser(id).isPresent() ? getUser(id).get() : null;

        double calculatingNetSalary = employee.getNetSalary();


        calculatingNetSalary = deductGrossSalary(calculatingNetSalary);

        employee.setGrossSalary((float) calculatingNetSalary);


        EmployeeDTO newEmployee =  EmployeeDTO.EmployeeDEOFunc(employee);

        return newEmployee ;
    }

    public List<Employee> ReturningList(int id){
        Optional<Employee> employee = employeeRepository.findById(id);

        //assert employee != null;
        return employee.get().getListOfEmployees();
    }


    public addEmployeeCommand GettingInfo(addEmployeeCommand employeeCommand) throws Exception{

        Optional<Employee> employee = getUser(employeeCommand.getIdEmployee());

        return employeeCommand;
    }

    public void ReplacingEmployeesToAnotherManager(Optional<Employee> employee){
        for (int i = 0; i < employee.get().getListOfEmployees().size(); i++) {
            employee.get().getListOfEmployees().get(i).setManager(employee.get().getManager());


        }

    }

    public List<Employee> getEmployeesUnderManagerRecursively(int id)
    {
        return employeeRepository.getEmployeesUnderManagerRecursively(id);
    }


}

