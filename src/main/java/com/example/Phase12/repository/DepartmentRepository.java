package com.example.Phase12.repository;

import com.example.Phase12.sections.Department;
import com.example.Phase12.sections.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends CrudRepository<Department, Integer> {


    }


