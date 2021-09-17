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

        if(employeeRepository.existsById(employee.getIdEmployee()))
            throw new BadArgumentsException("employee with this id is added before!");
        if(employee.getSecond_name() == null )
            throw new ResourceNotFoundException("The second_name is null");
        if(employee.getNational_id() == null)
            throw new ResourceNotFoundException("The national_id is null");
        if(Integer.parseInt(employee.getNational_id()) < 0)
            throw new BadArgumentsException("INVALID national-id");
        if(employee.getGrossSalary() == null)
            throw new ResourceNotFoundException("The gross salary is null");
        if(employee.getActive() == null)
            throw new ResourceNotFoundException("Employee.getActive is null");
        if(employee.getUsername() == null)
            throw new ResourceNotFoundException("Employee.username is null");
        if(employee.getPassword() == null)
            throw new ResourceNotFoundException("Employee.password is null");
        if(employee.getYearsOfExperience() == null)
            throw new ResourceNotFoundException("Employee.years_of_experience is null");
        if(employee.getRoles() == null)
            throw new ResourceNotFoundException("Employee.roles is null");

        if(Integer.parseInt(employee.getNational_id()) < 0)
            throw new BadArgumentsException("INVALID national-id");


        return employeeService.savingEmployee(employee);

    }

    @PutMapping(value = {"updating/{id}"})
    public addEmployeeDto setEmployeeService(@RequestBody addEmployeeCommand employeeCommand, @PathVariable int id) throws Exception {

        if(!employeeRepository.existsById(id))
            throw new ResourceNotFoundException("employee with this id is not found!");


        return employeeService.modifyUser(employeeCommand,id);
    }

    @DeleteMapping(value = "deleting/{id}")
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

    @RequestMapping(value = "gettingEmployeesRecursively/{id}")
    public List<Employee> getEmployeesUnderManagerRecursively(@PathVariable int id){
        if(!employeeRepository.existsById(id))
            throw new ResourceNotFoundException("The manager with this id not found!");
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

        if(!employeeRepository.existsById(id))
            throw new ResourceNotFoundException("manager with this id is not found!");

        return employeeService.ReturningList(id);
        }


}