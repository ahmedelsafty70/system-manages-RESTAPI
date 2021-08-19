package com.example.Phase12.controller;

import com.example.Phase12.Employee;
import com.example.Phase12.EmployeeDEO;
import com.example.Phase12.service.EmployeeService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value="/HumanResources")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping(value="/add")
    public void addingUser(@RequestBody Employee employee){
        employeeService.savingEmployee(employee);
    }

    @RequestMapping(value = "/gettingUser" )
    public Optional<Employee> getUser(int id){

        try {
            return employeeService.getUser(id);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping(value = "/submittingUser/{id}")
    public void addingEmployeesToManger(@RequestBody Employee employee, @PathVariable String id) throws NotFoundException{

        employeeService.puttingEmployeesUnderneathManger(employee,id);

    }
    @PutMapping(value = {"/updating/{id}"})
    public void setEmployeeService(@RequestBody Employee employee, @PathVariable String id) throws NotFoundException {

        employeeService.modifyUser(employee);

    }

    @DeleteMapping(value = "/deleting/{id}")
    public void deleteEmployee(@PathVariable int id) throws NotFoundException{


        Optional<Employee> employee = employeeService.getUser(id);
        if(employee.get().getManager() != null  && employee.get().getListOfEmployees() != null)
        {
            employeeService.ReplacingEmployeesToAnotherManager(employee);
        }
            employeeService.deleteEmployee(id);

    }

    @RequestMapping(value = "/gettingEmployeesRecursively/{id}")
    public List<Employee> getEmployeesUnderManagerRecursively(@PathVariable int id){
        return employeeService.getEmployeesUnderManagerRecursively(id);
    }

    @GetMapping(value = "/gettingSalary/{id}")
    public EmployeeDEO SalaryInfo(@PathVariable int id) throws NotFoundException {
        return employeeService.gettingSalaries(id);

    }

    @RequestMapping(value = "gettingEmployee/{id}")
    public Employee GettingInfoOfEmployee(@PathVariable String id) throws Exception {
        return employeeService.GettingInfo(id);

    }

    @GetMapping(value = "/gettingUnderEmployees/{id}")
        public List<Employee> EmployeesManager(@PathVariable int id) throws NotFoundException{
           return employeeService.ReturningList(id);
        }



}