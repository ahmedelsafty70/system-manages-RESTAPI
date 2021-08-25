package com.example.Phase12;

import com.example.Phase12.Repository.DepartmentRepository;
import com.example.Phase12.Repository.EmployeeRepository;
import com.example.Phase12.Repository.TeamRepository;
import com.example.Phase12.controller.DepartmentController;
import com.example.Phase12.service.EmployeeService;
import com.example.Phase12.service.Gender;
import com.example.Phase12.service.TeamService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import javassist.NotFoundException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.flywaydb.core.internal.database.base.Connection;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import javax.print.attribute.standard.MediaSize;
import javax.transaction.Transactional;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

import static java.lang.String.format;
import static org.hamcrest.Matchers.is;
import static org.dbunit.Assertion.assertEquals;
import static org.dbunit.Assertion.assertEqualsIgnoreCols;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@AutoConfigureMockMvc //handle the http request without calling server
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {Application.class})
@ExtendWith({SpringExtension.class})
@DatabaseSetup("/data.xml")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private TeamService teamService;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private DepartmentController departmentController;

    @Autowired
    private ObjectMapper objectMapper;


    @Test

    public void addEmployee() throws Exception {

        Calendar instance = Calendar.getInstance();
        instance.set(2000, 10, 10);
        Date dateOfBirth = instance.getTime();
        Date dateOfGraduation = instance.getTime();

        Employee employee1 = new Employee();
        Optional<Team> team = teamRepository.findById(1);
        employee1.setIdEmployee(9000);
        employee1.setGender(Gender.Female);
        employee1.setName("safty");
        employee1.setGrossSalary((float) 1000.65);
        employee1.setNetSalary((float) (1000.65F - (1000.65F * 0.15) + 500));
        employee1.setManager(employeeRepository.findById(1).get());
        employee1.setGraduationDate(dateOfGraduation);
        employee1.setDateOfBirth(dateOfBirth);
        int departmentId = 45;
        //Optional<Department> department = departmentRepository.findById(departmentId);

        System.out.println("\n \n \n *------------------" + employee1.getDateOfBirth());

        String employee = objectMapper.writeValueAsString(employee1);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/HumanResources/employees/add")
                .contentType(MediaType.APPLICATION_JSON).content(employee))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().json(employee));

        Employee employeeChecking = employeeRepository.findById(9000).orElse(null);

        Assertions.assertEquals(employee1.getIdEmployee(), employeeChecking.getIdEmployee());
        Assertions.assertEquals(employee1.getDateOfBirth(), employeeChecking.getDateOfBirth());
        Assertions.assertEquals(employee1.getName(), employeeChecking.getName());
        Assertions.assertEquals(employee1.getGender(), employeeChecking.getGender());
        Assertions.assertEquals(employee1.getDepartment(), employeeChecking.getDepartment());
        Assertions.assertEquals(employee1.getGrossSalary(), employeeChecking.getGrossSalary());


    }


//    @Test
//
//    public void AddEmployeesToManger() throws Exception {  //Done   (Works only with Debugging)
//        int idManager = 2;
//
//        Employee employee = employeeService.getUser(1).get();
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        String employeeJSON = objectMapper.writeValueAsString(employee);
//
//        this.mockMvc.perform(MockMvcRequestBuilders.post("/HumanResources/employees/submittingUser/" + idManager)
//                .contentType(MediaType.APPLICATION_JSON).content(employeeJSON))
//                .andExpect(status().isOk());
//        Optional<Employee> employeeForTesting = employeeRepository.findById(1);
//
//        Assertions.assertEquals(employee.getIdEmployee(), employeeForTesting.get().getIdEmployee());
//        //    Assertions.assertEquals(employee.getGender(),employeeForTesting.getGender());
//
//    }


    @Test   //Done
    public void DeletingEmployeeAndManagers() throws Exception {

       MvcResult employee = mockMvc.perform(MockMvcRequestBuilders.delete("/HumanResources/employees/deleting/2")).andExpect(status().isOk()).andReturn();
        Assertions.assertEquals(employeeRepository.existsById(2),false);

    }


    @Test

    public void GettingEmployeeInfo() throws Exception {  //Done
        Optional<Employee> employee = employeeRepository.findById(1);
        ObjectMapper objectMapper = new ObjectMapper();
        String Result = objectMapper.writeValueAsString(employee.get());
        this.mockMvc.perform(MockMvcRequestBuilders.get("/HumanResources/employees/gettingEmployee/1")).andExpect(status().isOk()).andExpect(content().json(Result));



    }


    @Test
    public void modify() throws JsonProcessingException {  //Done
        Employee employeeX = new Employee();

        employeeX.setName("farah");

        ObjectMapper objectMapper = new ObjectMapper();
        String JSONEmployee = objectMapper.writeValueAsString(employeeX);

        try {
            this.mockMvc.perform(MockMvcRequestBuilders.put("/HumanResources/employees/updating/5")
                    .contentType(MediaType.APPLICATION_JSON).content(JSONEmployee))
                    .andExpect(status().isOk()).andExpect(content().json(JSONEmployee));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Test

    public void GettingSalaryInfo() throws Exception {  //Done

        Employee employee = employeeRepository.findById(1).orElse(null);
        if(employee == null)
            throw new NotFoundException("didnt find this id");

        EmployeeDEO expectedDTO = EmployeeDEO.EmployeeDEOFunc(employee);

        ObjectMapper objectMapper = new ObjectMapper();
        String expectedDTOJSON = objectMapper.writeValueAsString(expectedDTO);

        System.out.println(">>>>>>>>>>>>>>" +expectedDTOJSON);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/HumanResources/employees/gettingSalary/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedDTOJSON));

        // Employee employeeToBeTest = employeeRepository.getById(1);
//
//        Assertions.assertEquals(employee.getGrossSalary(), employeeToBeTest.getGrossSalary());
    }

    @Test
//    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,value = "/expectedDepartment.xml")
    public void addDepartment() throws Exception {  //Done

        Department Engineering = new Department();
        Engineering.setId(45);
        Engineering.setName("Computer1");


        ObjectMapper objectMapper = new ObjectMapper();
        String y = objectMapper.writeValueAsString(Engineering);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/departmentController/adding").
                contentType(MediaType.APPLICATION_JSON).content(y)).
                andExpect(status().isOk());


        Optional<Department> department = departmentRepository.findById(45);
        Assertions.assertEquals(Engineering.getId(), department.get().getId());
        Assertions.assertEquals(Engineering.getName(), department.get().getName());

    }

    @Test
    public void AddTeam() throws Exception {  //Done

        Team team1 = new Team();
        team1.setTeamName("tiko");
        team1.setIdTeam(1);
        ObjectMapper objectMapper = new ObjectMapper();
        String JSONTeam = objectMapper.writeValueAsString(team1);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/teamController/adding").
                contentType(MediaType.APPLICATION_JSON).content(JSONTeam)).
                andExpect(status().isOk()).
                andExpect(content().json(JSONTeam));

        Optional<Team> team = teamRepository.findById(1);

        Assertions.assertEquals(team1.getIdTeam(), team.get().getIdTeam());
        Assertions.assertEquals(team1.getTeamName(), team1.getTeamName());


    }

    @Test

    public void getEmployeesInTeams() throws Exception {

        Optional<Team> team = teamRepository.findById(1);

        List<Employee> employees = teamService.EmployeesUnderTeam(team.get().getIdTeam());

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonEmployees = objectMapper.writeValueAsString(employees);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/teamController/gettingEmployeesUnderTeam/1").contentType(MediaType.APPLICATION_JSON).
                content(String.valueOf(team.get().getIdTeam()))).andExpect(status().isOk());

        Optional<Team> teamToCheckWith = teamRepository.findById(1);
        Assertions.assertEquals(team.get().getIdTeam(), teamToCheckWith.get().getIdTeam());
        Assertions.assertEquals(team.get().getTeamName(), teamToCheckWith.get().getTeamName());

    }


    @Test
    public void GettingEmployeesOfManager() throws Exception {

        Optional<Employee> manager = employeeRepository.findById(1);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/HumanResources/employees/gettingUnderEmployees/1")).andExpect(status().isOk());

        Employee managerForChecking = employeeRepository.getById(1);

        Assertions.assertEquals(manager.get().getIdEmployee(), managerForChecking.getIdEmployee());
    }

    @Test
    public void GettingEmployeesRecursively() throws Exception {
        List<Employee> employees = employeeRepository.getEmployeesUnderManagerRecursively(1);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonEmployees = objectMapper.writeValueAsString(employees);

        mockMvc.perform(MockMvcRequestBuilders.get("/HumanResources/employees/gettingEmployeesRecursively/1")).andExpect(status().isOk()).andExpect(content().json(jsonEmployees));


    }


}