package com.example.Phase12.repository;

import com.example.Phase12.sections.SalaryDetails;
import com.example.Phase12.sections.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalaryDetailsRepository extends JpaRepository<SalaryDetails, Integer> {

    @Query(value = "SELECT * FROM salary_details WHERE employee_id = :id ",nativeQuery = true)
    List<SalaryDetails> salaryOfSpecificEmployee(int id);
}
