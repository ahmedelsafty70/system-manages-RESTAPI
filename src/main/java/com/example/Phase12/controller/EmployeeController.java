package com.example.Phase12.controller;

import com.example.Phase12.commands.addEmployeeCommand;
import com.example.Phase12.exceptions.BadArgumentsException;
import com.example.Phase12.exceptions.ResourceNotFoundException;
import com.example.Phase12.repository.EmployeeRepository;
import com.example.Phase12.sections.Employee;
import com.example.Phase12.sections.EmployeeDTO;
import com.example.Phase12.service.EmployeeService;
import javassist.NotFoundException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static java.lang.Integer.parseInt;

@RestController
@RequestMapping(value="/HumanResources/employees")
public class EmployeeController {


    private EmployeeService employeeService;
    private EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeService employeeService, EmployeeRepository employeeRepository) {
        this.employeeService = employeeService;
        this.employeeRepository = employeeRepository;
    }

    @PostMapping(value="/add",produces=MediaType.APPLICATION_JSON_VALUE)
    public addEmployeeCommand addingUser(@RequestBody addEmployeeCommand employee) throws Exception {

        if(employeeRepository.existsById(employee.getIdEmployee()))
            throw new BadArgumentsException("employee with this id is added before!");
        if(employee.getSecond_name() == null )
            throw new ResourceNotFoundException("The second_name is null");
        if(employee.getGrossSalary() == null)
            throw new ResourceNotFoundException("The salary is null");
        if(employee.getGrossSalary() < 0)
            throw new BadArgumentsException("employee with is id is added before!");
        if(employee.getActive() == 0)
            throw new BadArgumentsException("Employee.getActive is null");
        if(employee.getNational_id().equals(null))
            throw new ResourceNotFoundException("The second_name is null");
        if(Integer.parseInt(employee.getNational_id()) < 0)
            throw new BadArgumentsException("INVALID national-id");


        return employeeService.savingEmployee(employee);

    }

    @PutMapping(value = {"/updating/{id}"})
    public addEmployeeCommand setEmployeeService(@RequestBody addEmployeeCommand employeeCommand, @PathVariable int id) throws Exception {

       return employeeService.modifyUser(employeeCommand,id);

    }

    @DeleteMapping(value = "/deleting/{id}")
    public void deleteEmployee(@PathVariable int id) throws Exception {

        if(!employeeRepository.existsById(id))
            throw new ResourceNotFoundException("employee with this id is not found!");

        Optional<Employee> employee = employeeService.getUser(id);
        if(employee.get().getManager() != null  && employee.get().getListOfEmployees() != null)
        {
            employeeService.ReplacingEmployeesToAnotherManager(employee);
            employeeService.deleteEmployee(id);
        }
        else
        {
            throw new Exception("can't delete employee with no manager!");
        }

    }

    @RequestMapping(value = "/gettingEmployeesRecursively/{id}")
    public List<Employee> getEmployeesUnderManagerRecursively(@PathVariable int id){
        return employeeService.getEmployeesUnderManagerRecursively(id);
    }

    @GetMapping(value = "/gettingSalary/{id}")
    public EmployeeDTO SalaryInfo(@PathVariable int id) throws NotFoundException {

        if(!employeeRepository.existsById(id))
            throw new ResourceNotFoundException("employee with this id is not found!");

        return employeeService.gettingSalaries(id);
    }

    @GetMapping("/users")
    public List<Employee> users(){
        return this.employeeRepository.findAll();
    }

    @GetMapping(value = "gettingEmployee")
    public addEmployeeCommand GettingInfoOfEmployee(@RequestBody addEmployeeCommand employeeCommand) throws Exception {

        if(!employeeRepository.existsById(employeeCommand.getIdEmployee()))
            throw new ResourceNotFoundException("employee with this id is not found!");

        return employeeService.GettingInfo(employeeCommand);
    }

    @GetMapping(value = "/gettingUnderEmployees/{id}") //batgeeb el ta7teeh bas
        public List<Employee> EmployeesManager(@PathVariable int id){

        if(!employeeRepository.existsById(id))
            throw new ResourceNotFoundException("employee with this id is not found!");

        return employeeService.ReturningList(id);
        }


}