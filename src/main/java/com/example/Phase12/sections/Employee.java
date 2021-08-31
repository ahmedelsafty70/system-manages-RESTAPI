package com.example.Phase12.sections;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "employee")
@DatabaseSetup("data.xml")

    public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEmployee")
    private int idEmployee;
    @Column(name = "name")
    private String name;
    @Column(name = "dateOfBirth")
    private Date dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    //@Enumerated(EnumType.STRING) //security
    private String roles;

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

    @Column(nullable = false)
    private String username;  //security

    @Column(nullable = false)
    private String password; //security

    private int active; //security

    private String permissions = ""; //security



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
        if(source.grossSalary !=0)
            destination.get().setGrossSalary(source.getGrossSalary());
    }


    public Employee() {

    }

    public Employee(String name, Gender gender) {
        this.name = name;

        this.gender = gender;
    }

    public Employee(String username, String password, String roles, String permissions){
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.permissions = permissions;
        this.active = 1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(int idEmployee) {
        this.idEmployee = idEmployee;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
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

    public List<String> getRoleList(){
        if(this.roles.length() > 0){
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }

    public List<String> getPermissionList(){
        if(this.permissions.length() > 0){
            return Arrays.asList(this.permissions.split(","));
        }
        return new ArrayList<>();
    }

}
