INSERT INTO Employee(username, password, roles,active)
VALUES('safty','$2a$10$v21ma/0iW089WQb17kg55.22L42JcQ94VIP0xkr38uBrXmhrDontC','USER',1);

INSERT INTO Employee(username,password, roles,permissions,active,years_of_experience,date_of_birth,gender,graduation_date,gross_salary,second_name,net_salary,department_id,manager_id,team_id,national_id,joined_year,degree_enum,bonus,raises)
VALUES('hr','$2a$12$wK1DatLPfrOpyuJp5g4VN.DFcIKvqMF8.zZ1vn3MKY01ATIYS1FEy','HR','gettingEmployees',1,10,'2020-5-15','Male','2020-5-14','34','mohamed','24',1,1,1,'18105254',2000,'Fresh','324.5','454.3');

INSERT INTO Employee(username, password, roles, active)
VALUES('manager','$2a$10$B3bCtAfwBA31XB9QK8ggHuHn9cFP0nztI6Gma/Zuv1EHEbYy3FQoe','MANAGER',1);
