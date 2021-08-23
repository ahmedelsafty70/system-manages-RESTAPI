package com.example.Phase12;

import com.example.Phase12.service.Gender;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "employee")
    public class Employee {

    @Id
    @Column(name = "idEmployee")
    private Integer idEmployee;
    @Column(name = "name")
    private String name;
    @Column(name = "dateOfBirth")
    private Date dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

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

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;


    @ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(name = "teamId")
    private Team team;




    public Employee(Employee source, Optional<Employee> destination){
        if(source.name != null)
            destination.get().setName(source.getName());
        if(source.dateOfBirth != null)
            destination.get().setDateOfBirth(source.getDateOfBirth());
        if (source.gender != null)
            destination.get().setDateOfBirth(source.getDateOfBirth());
        if(source.graduationDate != null)
            destination.get().setGraduationDate(source.getGraduationDate());
        if(source.department != null)
            destination.get().setDepartment(source.getDepartment());
//        if(source.manager != null)
//            destination.setManager(source.getManager());
        if(source.grossSalary != null)
            destination.get().setGrossSalary(source.getGrossSalary());
    }


    public Employee() {

    }

    public Employee(String name, Gender gender) {
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

    public Gender getGender() {
        return this.gender;
    }

    public void setGender(Gender gender) {
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

    public Float getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(Float netSalary) {
        this.netSalary = netSalary;
    }



    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }



}
