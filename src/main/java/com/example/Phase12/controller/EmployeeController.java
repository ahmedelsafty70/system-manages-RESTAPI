package com.example.Phase12.controller;

import com.example.Phase12.commands.employee.addEmployeeCommand;
import com.example.Phase12.dto.addEmployeeDto;
import com.example.Phase12.exceptions.BadArgumentsException;
import com.example.Phase12.exceptions.ResourceNotFoundException;
import com.example.Phase12.repository.EmployeeRepository;
import com.example.Phase12.sections.Employee;
import com.example.Phase12.sections.EmployeeDTO;
import com.example.Phase12.security.BaseController;
import com.example.Phase12.service.EmployeeService;
import javassist.NotFoundException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static java.lang.Integer.parseInt;

@RestController
@RequestMapping(value="/HumanResources/employees")
public class EmployeeController extends BaseController {


    private EmployeeService employeeService;
    private EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeService employeeService, EmployeeRepository employeeRepository) {
        this.employeeService = employeeService;
        this.employeeRepository = employeeRepository;
    }

    @PostMapping(value="add",produces=MediaType.APPLICATION_JSON_VALUE)
    public addEmployeeDto addingUser(@RequestBody addEmployeeCommand employee) throws Exception {

        return employeeService.savingEmployee(employee);

    }

    @PutMapping(value = {"updating/{id}"})
    public addEmployeeDto setEmployeeService(@RequestBody addEmployeeCommand employeeCommand, @PathVariable int id) throws Exception {

        return employeeService.modifyUser(employeeCommand,id);
    }

    @DeleteMapping(value = "deleting/{id}")
    public void deleteEmployee(@PathVariable int id) throws Exception {

        employeeService.deleteEmployee(id);

    }

    @RequestMapping(value = "gettingEmployeesRecursively/{id}")
    public List<Employee> getEmployeesUnderManagerRecursively(@PathVariable int id){

        return employeeService.getEmployeesUnderManagerRecursively(id);
    }


    @GetMapping(value = "gettingSalary/{idOfEmployee}")
    public EmployeeDTO SalaryInfoHRUser(@PathVariable int idOfEmployee) throws NotFoundException {

        if(employeeRepository.findById(idOfEmployee).orElse(null) == null)
            throw new ResourceNotFoundException("employee with this id is not found!");

        Employee employee = employeeRepository.findById(idOfEmployee).orElse(null);
        return employeeService.gettingSalaries(employee);
    }

    @GetMapping("users")
    public List<Employee> users(){
        return this.employeeRepository.findAll();
    }

    @GetMapping(value = "gettingEmployee/{id}")
    public addEmployeeDto GettingInfoOfEmployee(@PathVariable int id) throws Exception {

        if(!employeeRepository.existsById(id))
            throw new ResourceNotFoundException("employee with this id is not found!");
        Employee employee = employeeRepository.findById(id).orElse(null);
        return employeeService.GettingInfo(employee);
    }

    @GetMapping(value = "gettingUnderEmployees/{id}") //batgeeb el ta7teeh bas
        public List<Employee> EmployeesManager(@PathVariable int id){



        return employeeService.ReturningList(id);
        }


}