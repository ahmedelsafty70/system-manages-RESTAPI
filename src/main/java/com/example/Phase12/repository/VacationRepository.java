package com.example.Phase12.repository;

import com.example.Phase12.sections.Vacation;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface VacationRepository extends CrudRepository<Vacation, Integer> {


    @Transactional
    @Query(value = "SELECT COUNT(*) FROM vacation WHERE employee_id = :id AND current_year = :year", nativeQuery = true)
    int numberOfVacationDays(int id, int year);

    @Transactional
    @Query(value = "SELECT COUNT(*) FROM vacation WHERE employee_id = :id AND exceeded_day = 1 AND current_year = :year", nativeQuery = true)
    int counterForTheExceededDays(int id, int year);

    @Transactional
    @Modifying
    @Query(value = "UPDATE vacation  SET exceeded_day=0 WHERE employee_id = :id AND exceeded_day = 1 AND current_year = :year", nativeQuery = true)
    void resetExceededCounter(int id, int year);

    @Transactional
    @Query(value = "SELECT COUNT(*) FROM vacation WHERE employee_id = :id AND exceeded_day = 1", nativeQuery = true)
    int counterForTheExceededDays(int id);



}
