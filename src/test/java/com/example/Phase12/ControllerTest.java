package com.example.Phase12;

import com.example.Phase12.Repository.EmployeeRepository;
import com.example.Phase12.Repository.TeamRepository;
import com.example.Phase12.service.EmployeeService;
import com.example.Phase12.service.TeamService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
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
    private EmployeeService employeeService;
    @Autowired
    private TeamService teamService;

    @Autowired
    private TeamRepository teamRepository;

    @Test
    public void addEmployee() throws Exception {

        Employee safty = new Employee();
        Optional<Team> team = teamRepository.findById(1);
        safty.setGender("male");
        safty.setName("7amada");
        safty.setGrossSalary(545F);
        safty.setNetSalary(543F);
        safty.setTeam(team.get());

        ObjectMapper objectMapper = new ObjectMapper();
        String x = objectMapper.writeValueAsString(safty);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/HumanResources/add").contentType(MediaType.APPLICATION_JSON).content(x)).andExpect(status().isOk());

    }

    @Test
    public void AddEmployeesToManger() throws Exception {
        int idManager = 26;

        Employee employee = employeeService.getUser(24).get();


        ObjectMapper objectMapper = new ObjectMapper();
        String employeeJSON = objectMapper.writeValueAsString(employee);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/HumanResources/submittingUser/" + idManager).contentType(MediaType.APPLICATION_JSON).content(employeeJSON))
                .andExpect(status().isOk());

    }


    @Test//**************
    @Transactional
    public void whenDeleteByIdFromRepository_thenDeletingShouldBeSuccessful() throws Exception {


//        this.mockMvc.perform(MockMvcRequestBuilders.delete("/HumanResources/deleting/25")).andExpect(status().isOk());
        employeeRepository.deleteById(36);
    }


    @Test
    public void GettingEmployeeInfo() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/HumanResources/gettingEmployee/26")).andExpect(status().isOk());

    }

    @Test
    public void modify() throws JsonProcessingException {
        Employee employeeX = new Employee();

        employeeX.setName("yhyhyhyhy");

        ObjectMapper objectMapper = new ObjectMapper();
        String y = objectMapper.writeValueAsString(employeeX);

        try {
            this.mockMvc.perform(MockMvcRequestBuilders.put("/HumanResources/updating/7")
                    .contentType(MediaType.APPLICATION_JSON).content(y))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Test
    public void GettingSalaryInfo() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String y = objectMapper.writeValueAsString(10);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/HumanResources/gettingSalary/10")
                .contentType(MediaType.APPLICATION_JSON).content(y))
                .andExpect(status().isOk());

    }


    @Test
    public void addDepartment() throws Exception {

        Department Engineering = new Department();
        Engineering.setId(45);
        Engineering.setName("Computer");

        ObjectMapper objectMapper = new ObjectMapper();
        String y = objectMapper.writeValueAsString(Engineering);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/HumanResources/adding").contentType(MediaType.APPLICATION_JSON).content(y)).andExpect(status().isOk());

    }

    @Test
    public void AddTeam() throws Exception {

        Team team1 = new Team();
        team1.setTeamName("ganna");
        team1.setIdTeam(1);
        ObjectMapper objectMapper = new ObjectMapper();
        String JSONTeam = objectMapper.writeValueAsString(team1);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/teamController/adding").contentType(MediaType.APPLICATION_JSON).content(JSONTeam)).andExpect(status().isOk()).andExpect(content().json(JSONTeam));

    }

    @Test
    public void getEmployeesInTeams() throws Exception {
        Optional<Team> team = teamRepository.findById(1);
        List<Employee> employees = teamService.EmployeesUnderTeam(team.get().getIdTeam());
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonEmployees = objectMapper.writeValueAsString(employees);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/teamController/gettingEmployeesUnderTeam/1").contentType(MediaType.APPLICATION_JSON).
                content(String.valueOf(team.get().getIdTeam()))).andExpect(status().isOk()).andExpect(content().json(jsonEmployees));

    }


    @Test
    public void GettingEmployeesOfManager() throws Exception {

        Optional<Employee> manager = employeeRepository.findById(24);
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get("/HumanResources/gettingUnderEmployees/24")).andExpect(status().isOk()).andReturn();
    }

    @Test
    public void GettingEmployeesRecursively() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.get("/HumanResources/gettingEmployeesRecursively/38"));
    }


}