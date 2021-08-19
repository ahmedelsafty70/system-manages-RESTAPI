package com.example.Phase12.Repository;

import com.example.Phase12.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
   @Query(value = "with recursive cte as (\n" +
           "  select     id_employee, date_of_birth, gender, graduation_date, gross_salary, name, net_salary, department_id, manager_id, team_id\n" +
           "\n" +
           "  from       employee\n" +
           "  where      manager_id = ?1 \n" +
           "  union all\n" +
           "  select     p.id_employee, p.date_of_birth, p.gender, p.graduation_date, p.gross_salary, p.name, p.net_salary, p.department_id, p.manager_id, p.team_id\n" +
           "  from       employee p\n" +
           "  inner join cte\n" +
           "          on p.manager_id = cte.id_employee\n" +
           ")\n" +
           "select * from cte;", nativeQuery = true)
    List<Employee> getEmployeesUnderManagerRecursively(int id);
}