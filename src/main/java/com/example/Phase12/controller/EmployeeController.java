package com.example.Phase12.controller;

import com.example.Phase12.repository.EmployeeRepository;
import com.example.Phase12.sections.Employee;
import com.example.Phase12.sections.EmployeeDTO;
import com.example.Phase12.service.EmployeeService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static java.lang.Integer.parseInt;

@RestController
@RequestMapping(value="/HumanResources/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @PostMapping(value="/add",produces=MediaType.APPLICATION_JSON_VALUE)
    public Employee addingUser(@RequestBody Employee employee) throws Exception {

        return employeeService.savingEmployee(employee);

    }



    @PostMapping(value = "/submittingUser/{id}")
    public Employee addingEmployeesToManger(@RequestBody Employee employee, @PathVariable String id) throws Exception {

        return employeeService.puttingEmployeesUnderneathManger(employee,id);

    }
    @PutMapping(value = {"/updating/{id}"})
    public Employee setEmployeeService(@RequestBody Employee employee, @PathVariable String id) throws Exception {

       return employeeService.modifyUser(employee,parseInt(id));

    }

    @DeleteMapping(value = "/deleting/{id}")
    public void deleteEmployee(@PathVariable int id) throws Exception {


        Optional<Employee> employee = employeeService.getUser(id);
        if(employee.get().getManager() != null  && employee.get().getListOfEmployees() != null)
        {
            employeeService.ReplacingEmployeesToAnotherManager(employee);
            employeeService.deleteEmployee(id);
        }else
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
        return employeeService.gettingSalaries(id);

    }

    @GetMapping("/users")
    public List<Employee> users(){
        return this.employeeRepository.findAll();

    }

    @GetMapping(value = "gettingEmployee/{id}")
    public Employee GettingInfoOfEmployee(@PathVariable String id) throws Exception {
        return employeeService.GettingInfo(id);
    }

    @GetMapping(value = "/gettingUnderEmployees/{id}") //batgeeb el ta7teeh bas
        public List<Employee> EmployeesManager(@PathVariable int id) throws NotFoundException{
           return employeeService.ReturningList(id);
        }


}