package com.example.Phase12.service;

import com.example.Phase12.Employee;
import com.example.Phase12.EmployeeDEO;
import com.example.Phase12.Repository.EmployeeRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.Integer.parseInt;

@Service
public class EmployeeService {


    @Autowired
    public EmployeeRepository employeeRepository;


    public Employee savingEmployee(Employee employee) {

        return employeeRepository.save(employee);
    }

    public Optional<Employee> getUser (Integer id) throws NotFoundException
    {
        return employeeRepository.findById(id);
    }

    public Employee modifyUser(Employee employeeModifiedData , Integer id) throws NotFoundException {
       //
        // Employee empToModify = getUser(employeeModifiedData.getIdEmployee()).isPresent() ? getUser(employeeModifiedData.getIdEmployee()).get() : null;
        Employee finalModifiedOnEmployee;
        Optional<Employee> empToModify = getUser(id);
        Employee employeeWantedToBeChange = getUser(empToModify.get().getIdEmployee()).isPresent() ? getUser(empToModify.get().getIdEmployee()).get() : null;
        if(employeeWantedToBeChange == null)
            return null;
        else  {
          finalModifiedOnEmployee = new Employee(employeeModifiedData,empToModify);
          savingEmployee(finalModifiedOnEmployee);}

        return employeeModifiedData;
    }

    public Employee puttingEmployeesUnderneathManger(Employee employee , String id) throws NotFoundException {

        Optional<Employee> manager = getUser(parseInt(id));
        employee.setManager(manager.get());
        savingEmployee(employee);
        return employee;
    }

    public void deleteEmployee(int id){


        employeeRepository.deleteById(id);
       // employeeRepository.delete(employeeRepository.getById(id));
    }

    public EmployeeDEO gettingSalaries(int id) throws NotFoundException {
        Employee employee = getUser(id).isPresent() ? getUser(id).get() : null;
        EmployeeDEO newEmployee =  new EmployeeDEO();
        newEmployee = EmployeeDEO.EmployeeDEOFunc(employee);

        return newEmployee ;
    }

    public List<Employee> ReturningList(int id) throws NotFoundException{
        Employee employee = getUser(id).isPresent() ? getUser(id).get() : null;
        assert employee != null;
        return employee.getListOfEmployees();
    }


    public Employee GettingInfo(String id) throws Exception{

        Optional<Employee> employee = getUser(parseInt(id));
        return employee.get();
    }

    public void ReplacingEmployeesToAnotherManager(Optional<Employee> employee){
        for (int i = 0; i < employee.get().getListOfEmployees().size(); i++) {
            employee.get().getListOfEmployees().get(i).setManager(employee.get().getManager());
//            savingEmployee(employee.get().getListOfEmployees().get(i));

        }

    }

    public List<Employee> getEmployeesUnderManagerRecursively(int id)
    {
        return employeeRepository.getEmployeesUnderManagerRecursively(id);
    }

    public Float CalculateGrossSalary(Float netSalary) {
        double grossSalary = netSalary - (netSalary * 0.15) - 500;
        float grossSalaryFloat = (float)grossSalary;
        return grossSalaryFloat;
    }
}

