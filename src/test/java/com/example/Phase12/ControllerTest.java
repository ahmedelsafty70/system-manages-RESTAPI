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
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

import static java.lang.String.format;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc //handle the http request without calling server
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {Application.class})
//@SpringBootTest(classes = {Application.class})
public class ControllerTest {

    @LocalServerPort
    private int port;

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
        employee1.setGender(Gender.Female);
        employee1.setIdEmployee(90);
        employee1.setName("safty");
        employee1.setGrossSalary(1000.65F);
        employee1.setNetSalary((float) (1000.65F - (1000.65F * 0.15) + 500));

        employee1.setGraduationDate(dateOfGraduation);
        employee1.setDateOfBirth(dateOfBirth);
        int departmentId = 45;
        Optional<Department> department = departmentRepository.findById(departmentId);
        employee1.setDepartment(department.get());
        employee1.setTeam(team.get());
        System.out.println("\n \n \n *------------------" + employee1.getDateOfBirth());

        String employee = objectMapper.writeValueAsString(employee1);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/HumanResources/employees/add")
                .contentType(MediaType.APPLICATION_JSON).content(employee))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().json(employee));

    }

    @Test
    public void AddEmployeesToManger() throws Exception {  //Done   (Works only with Debugging)
        int idManager = 5;

        Employee employee = employeeService.getUser(6).get();


        ObjectMapper objectMapper = new ObjectMapper();
        String employeeJSON = objectMapper.writeValueAsString(employee);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/HumanResources/submittingUser/" + idManager)
                .contentType(MediaType.APPLICATION_JSON).content(employeeJSON))
                .andExpect(status().isOk());

    }


    @Test   //Done
    public void DeletingEmployeeAndManagers() throws Exception {


        this.mockMvc.perform(MockMvcRequestBuilders.delete("/HumanResources/deleting/3")).andExpect(status().isOk());

    }


    @Test
    public void GettingEmployeeInfo() throws Exception {  //Done

        this.mockMvc.perform(MockMvcRequestBuilders.get("/HumanResources/gettingEmployee/4")).andExpect(status().isOk());

    }

    @Test
    public void modify() throws JsonProcessingException {  //Done
        Employee employeeX = new Employee();

        employeeX.setName("farah");

        ObjectMapper objectMapper = new ObjectMapper();
        String JSONEmployee = objectMapper.writeValueAsString(employeeX);

        try {
            this.mockMvc.perform(MockMvcRequestBuilders.put("/HumanResources/updating/5")
                    .contentType(MediaType.APPLICATION_JSON).content(JSONEmployee))
                    .andExpect(status().isOk()).andExpect(content().json(JSONEmployee));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Test
    public void GettingSalaryInfo() throws Exception {  //Done

        Optional<Employee> employee = employeeService.getUser(4);

        ObjectMapper objectMapper = new ObjectMapper();
        String y = objectMapper.writeValueAsString(4);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/HumanResources/gettingSalary/4")
                .contentType(MediaType.APPLICATION_JSON).content(y))
                .andExpect(status().isOk());

    }


    @Test
    public void addDepartment() throws Exception {  //Done

        Department Engineering = new Department();
        Engineering.setId(45);
        Engineering.setName("Computer");

        ObjectMapper objectMapper = new ObjectMapper();
        String y = objectMapper.writeValueAsString(Engineering);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/departmentController/adding").
                contentType(MediaType.APPLICATION_JSON).content(y)).
                andExpect(status().isOk()).
                andExpect(content().json(y));

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

    }

    @Test
    public void getEmployeesInTeams() throws Exception {
        Optional<Team> team = teamRepository.findById(1);
        List<Employee> employees = teamService.EmployeesUnderTeam(team.get().getIdTeam());
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonEmployees = objectMapper.writeValueAsString(employees);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/teamController/gettingEmployeesUnderTeam/1").contentType(MediaType.APPLICATION_JSON).
                content(String.valueOf(team.get().getIdTeam()))).andExpect(status().isOk());

    }


    @Test
    public void GettingEmployeesOfManager() throws Exception {

        Optional<Employee> manager = employeeRepository.findById(24);
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get("/HumanResources/gettingUnderEmployees/24")).andExpect(status().isOk()).andReturn();
    }

    @Test
    public void GettingEmployeesRecursively() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/HumanResources/gettingEmployeesRecursively/38"));
    }


}