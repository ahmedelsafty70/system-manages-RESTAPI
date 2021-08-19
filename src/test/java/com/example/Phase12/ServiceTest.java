package com.example.Phase12;

import com.example.Phase12.service.DepartmentService;
import com.example.Phase12.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;


@AutoConfigureMockMvc //handle the http request without calling server
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class ServiceTest {

    @LocalServerPort
    private int port;

    @Autowired
    private EmployeeService employeeServiceTest;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private DepartmentService departmentService;


    @Test
    public void callingServiceEmployee() throws Exception{

        Employee x = new Employee();
//        x.getDepartment(13);
        x.setGender("Male");
        x.setName("ahmed");
//        x.setDateOfBirth(45/67/2000);
//        x.setGraduationDate(2000);

        Employee user = employeeServiceTest.savingEmployee(x);
        assertEquals(user.getName(), x.getName());

    }

    @Test
    public void callingServiceDepartment() throws Exception{

        Department x = new Department();
        x.setId(2);
        x.setName("Computer");

        Department user2 = departmentService.savingDepartment(x);
        assertEquals(user2.getName(),user2.getName());

    }




}
