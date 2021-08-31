package com.example.Phase12.controller;

import com.example.Phase12.sections.Department;
import com.example.Phase12.service.DepartmentService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/departmentController")
public class DepartmentController {
    
    @Autowired
    private DepartmentService departmentService;
    
    @RequestMapping(value = "/adding")
    public Department savingDepartment(@RequestBody Department department){
        return departmentService.savingDepartment(department);
    }

    @RequestMapping(value = "/GetDep" )
    public Optional<Department> getDep(){

        try {
            return departmentService.getDepartment(3);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    
}