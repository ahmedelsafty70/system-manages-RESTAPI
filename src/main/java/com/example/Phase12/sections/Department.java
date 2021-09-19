package com.example.Phase12.sections;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table

@Getter@Setter@NoArgsConstructor
public class Department {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "departmentName")
    private String name;
    @JsonIgnore
    @OneToMany(mappedBy = "department",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
        private List<Employee> employees;

    public Department(int id, String name, List<Employee> employees) {
        this.id = id;
        this.name = name;
        this.employees = employees;
    }

}
