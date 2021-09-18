package com.example.Phase12.controller;

import com.example.Phase12.dto.addDepartmentDto;
import com.example.Phase12.dto.addEmployeeDto;
import com.example.Phase12.exceptions.ResourceNotFoundException;
import com.example.Phase12.repository.DepartmentRepository;
import com.example.Phase12.repository.EmployeeRepository;
import com.example.Phase12.sections.Department;
import com.example.Phase12.sections.Employee;
import com.example.Phase12.sections.EmployeeDTO;
import com.example.Phase12.security.BaseController;
import com.example.Phase12.service.DepartmentService;
import com.example.Phase12.service.EmployeeService;
import javassist.NotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/HumanResources/notForHR")
public class NotForHRController extends BaseController {

    private EmployeeService employeeService;
    private EmployeeRepository employeeRepository;
    private DepartmentRepository departmentRepository;
    private DepartmentService departmentService;

    public NotForHRController(EmployeeService employeeService, EmployeeRepository employeeRepository, DepartmentRepository departmentRepository, DepartmentService departmentService) {
        this.employeeService = employeeService;
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.departmentService = departmentService;
    }

    @GetMapping(value = "gettingSalary")
    public EmployeeDTO SalaryInfo() throws NotFoundException {

        if(employeeRepository.findByUsername(getCurrentUser().getUsername()) == null)
            throw new ResourceNotFoundException("salary with this username is not found!");

        Employee employee = employeeService.findByUser(getCurrentUser().getUsername());
        return employeeService.gettingSalaries(employee);
    }

    @GetMapping(value = "gettingEmployee")
    public addEmployeeDto GettingInfoOfEmployee() throws Exception {

        if(employeeRepository.findByUsername(getCurrentUser().getUsername()) == null)
            throw new ResourceNotFoundException("employee with this username is not found!");

        Employee employee = employeeService.findByUser(getCurrentUser().getUsername());
        return employeeService.GettingInfo(employee);
    }




}
