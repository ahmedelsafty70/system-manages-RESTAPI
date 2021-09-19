package com.example.Phase12.controller;

import com.example.Phase12.commands.department.addDepartmentCommand;
import com.example.Phase12.dto.addDepartmentDto;
import com.example.Phase12.exceptions.BadArgumentsException;
import com.example.Phase12.exceptions.ResourceNotFoundException;
import com.example.Phase12.repository.DepartmentRepository;
import com.example.Phase12.sections.Department;
import com.example.Phase12.sections.Employee;
import com.example.Phase12.service.DepartmentService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/departmentController")
public class DepartmentController {
    

    private DepartmentService departmentService;
    private DepartmentRepository departmentRepository;

    public DepartmentController(DepartmentService departmentService, DepartmentRepository departmentRepository) {
        this.departmentService = departmentService;
        this.departmentRepository = departmentRepository;
    }

    @RequestMapping(value = "adding")
    public addDepartmentDto savingDepartment(@RequestBody addDepartmentCommand departmentCommand){

        return departmentService.savingDepartment(departmentCommand);
    }

    @RequestMapping(value = "GetDep/{id}")
    public addDepartmentDto getDep(@PathVariable int id){

        return departmentService.getDepartment(id);

    }
    
}