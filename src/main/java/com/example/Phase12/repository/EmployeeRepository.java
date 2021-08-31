package com.example.Phase12.repository;

import com.example.Phase12.sections.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
   @Query(value = "with recursive cte(id_employee, date_of_birth, gender, graduation_date, gross_salary, name, net_salary, department_id, manager_id, team_id) as (\n" +
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



    @Query("DELETE from Employee  where idEmployee =?1")
    @Modifying()
    void deleteById(Integer id);

    Employee findByUsername(String username);
}