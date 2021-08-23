create table team
(
    id_team   int not null
        primary key,
    team_name varchar(255) null
);

create table department
(
    id       int not null
        primary key,
    department_name varchar(255) null
);

create table employee
(
    id_employee     int not null
        primary key,
    date_of_birth   datetime null,
    gender          varchar(255) null,
    graduation_date datetime null,
    gross_salary    float null,
    name            varchar(255) null,
    net_salary      float null,
    department_id   int null,
    manager_id      int null,
    team_id         int null,
    constraint FK8d7lrsr6kwirr93rx0tafnoqa
        foreign key (team_id) references team (id_team),
    constraint FKbejtwvg9bxus2mffsm3swj3u9
        foreign key (department_id) references department (id),
    constraint FKou6wbxug1d0qf9mabut3xqblo
        foreign key (manager_id) references employee (id_employee)
);






