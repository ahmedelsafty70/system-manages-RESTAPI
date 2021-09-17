INSERT INTO team(id_team,team_name)
VALUES(1,'asfalt');

INSERT INTO department(id,department_name)
VALUES(1,'computer');


INSERT INTO Employee(id_employee,joined_year,date_of_birth,graduation_date,second_name,gender,net_salary,gross_salary,active,bonus
                    ,raises,years_of_experience,permissions, password,username, roles,national_id,degree_enum,team_id,department_id)
VALUES(1,'2020','2020-3-12 12:00:00','2020-3-12 12:00:00','lil','Male','800.6','67.7','1','466','453','100','gettingEmployees','$2a$10$v21ma/0iW089WQb17kg55.22L42JcQ94VIP0xkr38uBrXmhrDontC','safty','USER','18105254','Fresh','1','1');

INSERT INTO Employee(id_employee,joined_year,date_of_birth,graduation_date,second_name,gender,net_salary,gross_salary,active,bonus
                    ,raises,years_of_experience,permissions, password,username, roles,national_id,degree_enum,team_id,department_id,manager_id)
VALUES(2,'2020','2020-3-12 12:00:00','2020-3-12 12:00:00','bob','Male','800.6','67.7','1','466','453','100','gettingEmployees','$2a$12$wK1DatLPfrOpyuJp5g4VN.DFcIKvqMF8.zZ1vn3MKY01ATIYS1FEy','spongBob','HR','18105255','Fresh','1','1','1');

INSERT INTO Employee(id_employee,joined_year,date_of_birth,graduation_date,second_name,gender,net_salary,gross_salary,active,bonus
                    ,raises,years_of_experience,permissions, password,username, roles,national_id,degree_enum,team_id,department_id,manager_id)
VALUES(3,'2020','2020-3-12 12:00:00','2020-3-12 12:00:00','mona','Female','800.6','67.7','1','466','453','100','gettingEmployees','$2a$10$B3bCtAfwBA31XB9QK8ggHuHn9cFP0nztI6Gma/Zuv1EHEbYy3FQoe','manager','MANAGER','18106135','Fresh','1','1','2');

INSERT INTO vacation(id,employee_name,current_year,exceeded_day,employee_id)
VALUES(1,'lil','2021',0,1);

INSERT INTO salary_details(id,actual_salary,date,employee_id)
VALUES(1,'24','2020-03-12',1)