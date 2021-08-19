package com.example.Phase12.Repository;

import com.example.Phase12.Department;
import com.example.Phase12.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface departmentRepository extends CrudRepository<Department, Integer> {


    }


