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
    gender          varchar(255) null,
    graduation_date datetime null,
    gross_salary    float null,
    name            varchar(255) null,
    net_salary      float null,
    department_id   integer null,
    manager_id      integer null,
    team_id         integer null,
    username        varchar(255) null,
    password        varchar(255) null,
    active          integer null,
    permissions     varchar(255) null,
    roles           varchar(255) null,

    constraint FK8d7lrsr6kwirr93rx0tafnoqa
        foreign key (team_id) references team (id_team),
    constraint FKbejtwvg9bxus2mffsm3swj3u9
        foreign key (department_id) references department (id),
    constraint FKou6wbxug1d0qf9mabut3xqblo
        foreign key (manager_id) references employee (id_employee)
);






