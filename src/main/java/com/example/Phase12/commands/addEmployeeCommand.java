package com.example.Phase12.commands;

public class addEmployeeCommand {

    private int idEmployee;
    private String second_name;
    private String roles;
    private String national_id;
    private String username;
    private String password;
    private int active;
    private int yearsOfExperience;
    private Float grossSalary;

    public addEmployeeCommand() {
    }

    public addEmployeeCommand(int idEmployee, String second_name, String roles, String national_id, String username, String password, int active, int yearsOfExperience, Float grossSalary) {
        this.idEmployee = idEmployee;
        this.second_name = second_name;
        this.roles = roles;
        this.national_id = national_id;
        this.username = username;
        this.password = password;
        this.active = active;
      //  this.yearsOfExperience = yearsOfExperience;
        this.grossSalary = grossSalary;
    }

    public int getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(int idEmployee) {
        this.idEmployee = idEmployee;
    }

    public String getSecond_name() {
        return second_name;
    }

    public void setSecond_name(String second_name) {
        this.second_name = second_name;
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

    public String getNational_id() {
        return national_id;
    }

    public void setNational_id(String national_id) {
        this.national_id = national_id;
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

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
//
//    public int getYearsOfExperience() {
//        return yearsOfExperience;
//    }
//
//    public void setYearsOfExperience(int yearsOfExperience) {
//        this.yearsOfExperience = yearsOfExperience;
//    }
}
