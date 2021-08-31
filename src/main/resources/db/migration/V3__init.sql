INSERT INTO Employee(username, password, roles,active)
VALUES('safty','$2a$10$v21ma/0iW089WQb17kg55.22L42JcQ94VIP0xkr38uBrXmhrDontC','USER',1);


INSERT INTO Employee(username,password, roles,permissions,active)
VALUES('admin','$2a$10$f961TlPMQrkpa8eK07TfIeMoQ9TzclGoHDSVh0CGyaMUPPMPVwhZO','ADMIN','gettingEmployees',1);

INSERT INTO Employee(username, password, roles, active)
VALUES('manager','$2a$10$B3bCtAfwBA31XB9QK8ggHuHn9cFP0nztI6Gma/Zuv1EHEbYy3FQoe','MANAGER',1);
