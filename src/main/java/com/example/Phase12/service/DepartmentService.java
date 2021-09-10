package com.example.Phase12.service;

import com.example.Phase12.commands.addDepartmentCommand;
import com.example.Phase12.repository.DepartmentRepository;
import com.example.Phase12.sections.Department;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DepartmentService {


    public DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public addDepartmentCommand savingDepartment(addDepartmentCommand departmentCommand) {

        Department department = new Department();

        department.setName(departmentCommand.getName());
        department.setId(department.getId());

        departmentRepository.save(department);

        return departmentCommand;
    }

    public Optional<Department> getDepartment(int id){
        return departmentRepository.findById(id);
    }

}
