create table team
(
    id_team   integer AUTO_INCREMENT not null  primary key,
    team_name varchar(255) null
);

create table department
(
    id       integer AUTO_INCREMENT not null
        primary key,
    department_name varchar(255) null
);

create table employee
(
    id_employee     integer AUTO_INCREMENT not null
        primary key,
    date_of_birth   datetime null,
    gender          varchar(255) not null,
    graduation_date datetime null,
    gross_salary    float not null,
--     first_name      varchar(255) null,
    second_name     varchar(255) not null,
    net_salary      float null,
    department_id   integer null,
    manager_id      integer null,
    team_id         integer null,
    username        varchar(255) null,
    password        varchar(255) null,
    active          integer null,
    permissions     varchar(255) null,
    roles           varchar(255) null,
    national_id     varchar(255) not null unique,
    joined_year     integer null,
    degree_enum     varchar(255) not null,
    bonus       double null,
    raises      double null,
    years_of_experience integer null,


    constraint FK8d7lrsr6kwirr93rx0tafnoqa
        foreign key (team_id) references team (id_team),
    constraint FKbejtwvg9bxus2mffsm3swj3u9
        foreign key (department_id) references department (id),
    constraint FKou6wbxug1d0qf9mabut3xqblo
        foreign key (manager_id) references employee (id_employee)
);

create table vacation
(
    id       integer AUTO_INCREMENT not null
        primary key,
    employee_name varchar(255) null,
    current_year          integer null,
    exceeded_day  integer null,
    employee_id   integer null,

    constraint vacations_employee_employeeId_fk
        foreign key (employee_id) references   employee  (id_employee)

);

create table Earnings
(
    id      integer AUTO_INCREMENT
        primary key,
    raises     double null,
    bonus     double null,
    date      DATE null,
    employee_id int null,

    constraint earnings_employee_employeeId_fk
        foreign key (employee_id) references   employee  (id_employee)
);

create table salary_details
(
    id       integer AUTO_INCREMENT not null
        primary key,
--     gross_salary float null,
--     net_salary   float null,
    actual_salary   float null,
    date        Date  null,
  --  raises      double null,
    employee_id   integer null,


    constraint SalaryDetails_employee_employeeId_fk
        foreign key (employee_id) references   employee  (id_employee)

)




