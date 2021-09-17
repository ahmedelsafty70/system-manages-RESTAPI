package com.example.Phase12.repository;

import com.example.Phase12.sections.Department;
import com.example.Phase12.sections.Earnings;
import org.springframework.data.repository.CrudRepository;

public interface EarningRepository extends CrudRepository<Earnings, Integer> {
}
