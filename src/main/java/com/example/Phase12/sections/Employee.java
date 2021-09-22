package com.example.Phase12.sections;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.*;

@Entity
//@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
@Table(name = "employee")

    public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEmployee")
    private int idEmployee;

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

//    @JsonIgnore
    @JsonManagedReference
    @OneToMany(mappedBy="employee", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Earnings> ListOfEarnings ;

    @Column(name = "netSalary")
    private Float netSalary;

    @Column(name = "grossSalary")
    private Float grossSalary;


    @Column(name = "first_name")
    private String first_name;

    @JsonIgnore
    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Employee> listOfEmployees;

    @JsonBackReference
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
    @ManyToOne
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
    private DegreeEnum degreeEnum;

    @Column(name = "years_of_experience")
    private int yearsOfExperience;

    public Employee() {

    }

    public Employee(Gender gender) {
        this.gender = gender;
    }

    public Employee(String username, String password, String roles, String permissions){
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.permissions = permissions;
        this.active = 1;
    }

    public Employee(Integer idEmployee, String second_name, String roles, String national_id, String username, String password, Integer active, Integer yearsOfExperience, Float grossSalary){

        this.idEmployee = idEmployee;
        this.second_name = second_name;
        this.roles = roles;
        this.national_id = national_id;
        this.username = username;
        this.password = password;
        this.active = active;
        this.yearsOfExperience = yearsOfExperience;
        this.grossSalary = grossSalary;
    }
    public int getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(int idEmployee) {
        this.idEmployee = idEmployee;
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

    public List<Earnings> getListOfEarnings() {
        return ListOfEarnings;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getNational_id() {
        return national_id;
    }

    public void setNational_id(String national_id) {
        this.national_id = national_id;
    }


    public void setJoined_year(Integer joined_year) {
        this.joined_year = joined_year;
    }

    public Date getGraduationDate() {
        return graduationDate;
    }

    public void setGraduationDate(Date graduationDate) {
        this.graduationDate = graduationDate;
    }


    public void setListOfEarnings(List<Earnings> listOfEarnings) {
        ListOfEarnings = listOfEarnings;
    }

    public void setListOfEmployees(List<Employee> listOfEmployees) {
        this.listOfEmployees = listOfEmployees;
    }

    public List<Vacation> getVacationList() {
        return vacationList;
    }

    public void setVacationList(List<Vacation> vacationList) {
        this.vacationList = vacationList;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public Double getBonus() {
        return bonus;
    }

    public void setBonus(Double bonus) {
        this.bonus = bonus;
    }
    public DegreeEnum getDegreeEnum() {
        return degreeEnum;
    }

    public void setDegreeEnum(DegreeEnum degreeEnum) {
        this.degreeEnum = degreeEnum;
    }
    public Gender getGender() {
        return this.gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
    public Integer getJoined_year() {
        return joined_year;
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
    public Float getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(Float netSalary) {
        this.netSalary = netSalary;
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
