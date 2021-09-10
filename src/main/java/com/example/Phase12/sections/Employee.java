package com.example.Phase12.sections;


import com.example.Phase12.commands.addEmployeeCommand;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "employee")

    public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEmployee")
    private int idEmployee;
//    @Column(name = "name")
//    private String name;

    @Column(name = "second_name")
    private String second_name;

    @Column(name = "dateOfBirth")
    private Date dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "bonus")
    private Double bonus=0D;

    @Column(name = "raises")
    private Double raises=0D;


    @Column(name = "joined_year")
    private Integer joined_year;

    private String roles;   //security

    @Column(name = "graduationDate")
    private Date graduationDate;



    @Column(name = "netSalary")
    private Float netSalary;

    @Column(name = "grossSalary")
    private Float grossSalary;

    @JsonIgnore
    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Employee> listOfEmployees;

    @JsonIgnore
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Vacation> vacationList;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Employee manager;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;


    @JsonIgnore
    @ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(name = "teamId")
    private Team team;

    @Column(name = "national_id")
    private String national_id;

    @Column(nullable = false)
    private String username;  //security

    @Column(nullable = false)
    private String password; //security

    private int active; //security

    private String permissions = ""; //security

    @JsonIgnore
    @OneToMany(mappedBy = "employee",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<SalaryDetails> listOfSalaryHistories;

    @Enumerated(EnumType.STRING)
    private DegreeEnum degree_enum;

    @Column(name = "years_of_experience")
    private int yearsOfExperience;



    public Employee(addEmployeeCommand source, Optional<Employee> destination){
//        if(source.first_name != null)
//            destination.get().setFirst_name(source.getFirst_name());
        if(source.getSecond_name() != null)
            destination.get().setSecond_name(source.getSecond_name());
        if(source.getRoles() != null)
            destination.get().setRoles(source.getRoles());
        if (source.getNational_id() != null)
            destination.get().setNational_id(source.getNational_id());
        if(source.getActive() != 0)
            destination.get().setActive(source.getActive());
        if(source.getPassword() != null)
            destination.get().setPassword(source.getPassword());
        if(source.getUsername() != null)
            destination.get().setUsername(source.getUsername());
//        if(source.getYearsOfExperience() !=0)
//            destination.get().setYearsOfExperience(source.getYearsOfExperience());
        if(source.getGrossSalary() != null)
            destination.get().setGrossSalary(source.getGrossSalary());
    }


    public Employee() {

    }

    public Employee(String name, Gender gender) {
//        this.name = name;

        this.gender = gender;
    }

    public Employee(String username, String password, String roles, String permissions,String first_name){
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.permissions = permissions;
//        this.first_name=first_name;
        this.active = 1;
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

    public Double getRaises() {
        return raises;
    }

    public void setRaises(Double raises) {
        this.raises = raises;
    }

    public DegreeEnum getDegreeEnum() {
        return degree_enum;
    }

    public void setDegreeEnum(DegreeEnum degreeEnum) {
        this.degree_enum = degreeEnum;
    }

    public String getNational_id() {
        return national_id;
    }

    public void setNational_id(String national_id) {
        this.national_id = national_id;
    }

    public Double getBonus() {
        return bonus;
    }

    public void setBonus(Double bonus) {
        this.bonus = bonus;
    }

    public DegreeEnum getDegree_enum() {
        return degree_enum;
    }

    public void setDegree_enum(DegreeEnum degree_enum) {
        this.degree_enum = degree_enum;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
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

    public Integer getJoined_year() {
        return joined_year;
    }

    public void setJoined_year(Integer joined_year) {
        this.joined_year = joined_year;
    }

    public Department getDepartment() {
        return department;
    }

    public List<Vacation> getVacationList() {
        return vacationList;
    }

    public void setVacationList(List<Vacation> vacationList) {
        this.vacationList = vacationList;
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

    public List<SalaryDetails> getListOfSalaryHistories() {
        return listOfSalaryHistories;
    }

    public void setListOfSalaryHistories(List<SalaryDetails> listOfSalaryHistories) {
            listOfSalaryHistories = listOfSalaryHistories;
    }

    public String getSecond_name() {
        return second_name;
    }

    public void setSecond_name(String second_name) {
        this.second_name = second_name;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }


    @JsonIgnore
    public List<String> getRoleList(){
        if(this.roles.length() > 0){
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }


    @JsonIgnore
    public List<String> getPermissionList(){
        if(this.permissions.length() > 0){
            return Arrays.asList(this.permissions.split(","));
        }
        return new ArrayList<>();
    }

}
