package com.example.Phase12.repository;

import com.example.Phase12.sections.SalaryDetails;
import com.example.Phase12.sections.Team;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaryDetailsRepository extends CrudRepository<SalaryDetails, Integer> {

    
}
