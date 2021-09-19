package com.example.Phase12.service;

import com.example.Phase12.commands.employee.addEmployeeCommand;
import com.example.Phase12.dto.addEmployeeDto;
import com.example.Phase12.exceptions.BadArgumentsException;
import com.example.Phase12.exceptions.ResourceNotFoundException;
import com.example.Phase12.repository.EmployeeRepository;
import com.example.Phase12.sections.ConstantsDeduction;
import com.example.Phase12.sections.Employee;
import com.example.Phase12.sections.EmployeeDTO;
import com.example.Phase12.security.BaseController;
import com.fasterxml.jackson.databind.ObjectMapper;
import javassist.NotFoundException;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.Integer.parseInt;

@Service
public class EmployeeService extends BaseController {


    public EmployeeRepository employeeRepository;
    private PasswordEncoder passwordEncoder;
    private ModelMapper modelMapper;


    public EmployeeService(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    public Employee mapToEmployee(addEmployeeCommand employeeCommand) {
        Employee employee = modelMapper.map(employeeCommand, Employee.class);
        return employee;
    }

    public addEmployeeDto savingEmployee(addEmployeeCommand addEmployeeCommand) throws Exception {

        if(employeeRepository.existsById(addEmployeeCommand.getIdEmployee()))
            throw new BadArgumentsException("employee with this id is added before!");
        if(addEmployeeCommand.getSecond_name() == null )
            throw new ResourceNotFoundException("The second_name is null");
        if(addEmployeeCommand.getNational_id() == null)
            throw new ResourceNotFoundException("The national_id is null");
        if(parseInt(addEmployeeCommand.getNational_id()) < 0)
            throw new BadArgumentsException("INVALID national-id");
        if(addEmployeeCommand.getGrossSalary() == null)
            throw new ResourceNotFoundException("The gross salary is null");
        if(addEmployeeCommand.getActive() == null)
            throw new ResourceNotFoundException("Employee.getActive is null");
        if(addEmployeeCommand.getUsername() == null)
            throw new ResourceNotFoundException("Employee.username is null");
        if(addEmployeeCommand.getPassword() == null)
            throw new ResourceNotFoundException("Employee.password is null");
        if(addEmployeeCommand.getYearsOfExperience() == null)
            throw new ResourceNotFoundException("Employee.years_of_experience is null");
        if(addEmployeeCommand.getRoles() == null)
            throw new ResourceNotFoundException("Employee.roles is null");

        if(parseInt(addEmployeeCommand.getNational_id()) < 0)
            throw new BadArgumentsException("INVALID national-id");

        Employee employee = mapToEmployee(addEmployeeCommand);

        addEmployeeDto employeeDto = mapToEmployeeDto(employeeRepository.save(employee));

        return employeeDto;
    }

    public addEmployeeDto mapToEmployeeDto(Employee employee) {
        addEmployeeDto employeeDto = modelMapper.map(employee, addEmployeeDto.class);

        return employeeDto;
    }

    public Employee calculateNetSalary(Employee employee) {


        double net_salary = employee.getGrossSalary() - ConstantsDeduction.insurance;  // No Bonus And Raises In The First Time

        employee.setNetSalary((float) (net_salary - (net_salary * ConstantsDeduction.taxes)));

        return employee;
    }

    public Optional<Employee> getUser(Integer id) throws NotFoundException {
        return employeeRepository.findById(id);
    }

    public addEmployeeDto modifyUser(addEmployeeCommand employeeModifiedData, Integer id) throws Exception {

        if(!employeeRepository.existsById(id))
            throw new ResourceNotFoundException("employee with this id is not found!");


        Employee empToModify = employeeRepository.findById(id).orElse(null);
        if (empToModify == null)
            return null;
        employeeModifiedData.dtoToEmployee(employeeModifiedData, empToModify);
        Employee employee = employeeRepository.save(empToModify);

        return mapToDto(employee);
    }

    private addEmployeeDto mapToDto(Employee employee) {
        addEmployeeDto employeeDto = modelMapper.map(employee, addEmployeeDto.class);
        return employeeDto;
    }


    public void deleteEmployee(int id) throws Exception {

        if(!employeeRepository.existsById(id))
            throw new ResourceNotFoundException("employee with this id is not found!");

        Optional<Employee> employee = getUser(id);
        if(employee.get().getManager() != null  && employee.get().getListOfEmployees() != null)
        {
            ReplacingEmployeesToAnotherManager(employee);

        }
        else
        {
            throw new Exception("can't delete employee with no manager!");
        }

        employeeRepository.deleteById(id);
    }


    public float deductGrossSalary(double grossSalary) {
        double netSalary = grossSalary - (grossSalary * 0.15) - 500;
        float netSalaryFloat = (float) netSalary;
        return netSalaryFloat;
    }
    public Employee findByUser(String username) {
        return employeeRepository.findByUsername(username);
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
    public EmployeeDTO gettingSalaries(Employee employee) throws NotFoundException {

        double calculatingNetSalary = employee.getNetSalary();
        employee.setGrossSalary((float) deductGrossSalary(calculatingNetSalary));
        EmployeeDTO newEmployee = EmployeeDTO.EmployeeDEOFunc(employee);

        return newEmployee;
    }

    public List<Employee> ReturningList(int id) {

        if(!employeeRepository.existsById(id))
            throw new ResourceNotFoundException("manager with this id is not found!");
        Optional<Employee> employee = employeeRepository.findById(id);

        return employee.get().getListOfEmployees();
    }


    public addEmployeeDto GettingInfo(Employee employee) throws Exception {


        addEmployeeDto employeeDto = mapToEmployeeDto(employee);

        return employeeDto;
    }

    public void ReplacingEmployeesToAnotherManager(Optional<Employee> employee) {
        for (int i = 0; i < employee.get().getListOfEmployees().size(); i++) {
            employee.get().getListOfEmployees().get(i).setManager(employee.get().getManager());
        }
    }

    public List<Employee> getEmployeesUnderManagerRecursively(int id) {

        if(!employeeRepository.existsById(id))
            throw new ResourceNotFoundException("The manager with this id not found!");

        Employee employee = employeeRepository.findById(id).orElse(null);
        List<Employee> ret = new ArrayList<>();
        if(employee!=null){
            if(employee.getListOfEmployees()==null) return ret;
            for(var emp : employee.getListOfEmployees())
            {
                ret.add(emp);
                ret.addAll(getEmployeesUnderManagerRecursively(emp.getIdEmployee()));
            }
        }

        return ret;
        //return employeeRepository.getEmployeesUnderManagerRecursively(id);
    }


}

