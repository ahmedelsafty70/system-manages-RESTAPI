package com.example.Phase12.repository;

import com.example.Phase12.sections.Department;
import com.example.Phase12.sections.Earnings;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface EarningRepository extends CrudRepository<Earnings, Integer> {



}