package com.example.Phase12;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Department {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "departmentName")
    private String name;

    @OneToMany(mappedBy = "department")
        private List<Employee> employees;

    public Department() {
    }

    public Department(int id, String name, List<Employee> employees) {
        this.id = id;
        this.name = name;
        this.employees = employees;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
