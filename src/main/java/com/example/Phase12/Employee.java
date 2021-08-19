package com.example.Phase12;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "employee")
    public class Employee {

    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    @Column(name = "idEmployee")
    private Integer idEmployee;
    @Column(name = "name")
    private String name;
    @Column(name = "dateOfBirth")
    private Date dateOfBirth;
    @Column(name = "gender")
    private String gender;
    @Column(name = "graduationDate")
    private Date graduationDate;


    @Column(name = "netSalary")
    private Float netSalary;
    @Column(name = "grossSalary")
    private Float grossSalary;

    @JsonIgnore
    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Employee> listOfEmployees;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Employee manager;

    @ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;


    @ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(name = "teamId")
    private Team team;




    public Employee(Employee source, Employee destination){
        if(source.name != null)
            destination.setName(source.getName());
        if(source.dateOfBirth != null)
            destination.setDateOfBirth(source.getDateOfBirth());
        if (source.gender != null)
            destination.setDateOfBirth(source.getDateOfBirth());
        if(source.graduationDate != null)
            destination.setGraduationDate(source.getGraduationDate());
        if(source.department != null)
            destination.setDepartment(source.getDepartment());
//        if(source.manager != null)
//            destination.setManager(source.getManager());
        if(source.grossSalary != null)
            destination.setGrossSalary(source.getGrossSalary());
    }


    public Employee() {

    }

    public Employee(String name, String gender) {
        this.name = name;

        this.gender = gender;
//        this.graduationDate = graduationDate;
//        this.department = department;
//        this.manager = manager;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(Integer idEmployee) {
        this.idEmployee = idEmployee;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender(String male) {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getGraduationDate() {
        return graduationDate;
    }

    public void setGraduationDate(Date graduationDate) {
        this.graduationDate = graduationDate;
    }

    public Department getDepartment() {
        return department;
    }

    public Float getGrossSalary() {
        return grossSalary;
    }

    public void setGrossSalary(Float grossSalary) {
        this.grossSalary = grossSalary;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    public List<Employee> getListOfEmployees() {
        return listOfEmployees;
    }

    public void setListOfEmployees(List<Employee> listOfEmployees) {
        this.listOfEmployees = listOfEmployees;
    }
//    public Employee getManager() {
//        return manager;
//    }
//
//    public void setManager(Employee manager) {
//        this.manager = manager;
//    }

    public Float getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(Float netSalary) {
        this.netSalary = netSalary;
    }

    public String getGender() {
        return gender;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
