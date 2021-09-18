package com.example.Phase12.service;

import com.example.Phase12.commands.department.addDepartmentCommand;
import com.example.Phase12.dto.addDepartmentDto;
import com.example.Phase12.repository.DepartmentRepository;
import com.example.Phase12.repository.EmployeeRepository;
import com.example.Phase12.sections.Department;
import com.example.Phase12.sections.Employee;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {


    private DepartmentRepository departmentRepository;
    private ModelMapper modelMapper;
    private EmployeeRepository employeeRepository;


    public DepartmentService(DepartmentRepository departmentRepository, ModelMapper modelMapper, EmployeeRepository employeeRepository) {
        this.departmentRepository = departmentRepository;
        this.modelMapper = modelMapper;
        this.employeeRepository = employeeRepository;
    }

    private Department mapToDepartment(addDepartmentCommand departmentCommand){
        Department department = modelMapper.map(departmentCommand,Department.class);

        return department;
    }

    public addDepartmentDto savingDepartment(addDepartmentCommand departmentCommand) {

        Department department = mapToDepartment(departmentCommand);

        Department departmentSaved = departmentRepository.save(department);

        addDepartmentDto departmentDto = new addDepartmentDto(departmentSaved.getId(),departmentSaved.getName());

        return departmentDto;
    }

    private addDepartmentDto mapToDepartmentDto(Department department){
        addDepartmentDto departmentDto = modelMapper.map(department,addDepartmentDto.class);

        return departmentDto;
    }


    public addDepartmentDto getDepartment(Department department){

       //  Department department = departmentRepository.findById(id).orElse(null);

         addDepartmentDto departmentDto = mapToDepartmentDto(department);
         return departmentDto;
    }

}
