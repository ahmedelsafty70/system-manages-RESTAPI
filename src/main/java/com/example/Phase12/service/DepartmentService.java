package com.example.Phase12.service;

import com.example.Phase12.Department;
import com.example.Phase12.Repository.DepartmentRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired
    public DepartmentRepository departmentRepository;

    public Department savingDepartment(Department department){
        return departmentRepository.save(department);
    }

    public Optional<Department> getDepartment(int id) throws NotFoundException
    {
        return departmentRepository.findById(id);
    }

}
