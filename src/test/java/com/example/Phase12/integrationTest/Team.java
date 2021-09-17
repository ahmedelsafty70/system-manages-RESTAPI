package com.example.Phase12.integrationTest;

import com.example.Phase12.QuartzTest;
import com.example.Phase12.ScheduledConfig;
import com.example.Phase12.commands.team.addTeamCommand;
import com.example.Phase12.commands.team.addTeamCommandTeamIdException;
import com.example.Phase12.exceptions.BadArgumentsException;
import com.example.Phase12.exceptions.ResourceNotFoundException;
import com.example.Phase12.repository.*;
import com.example.Phase12.sections.Employee;
import com.example.Phase12.service.TeamService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles("test")
@AutoConfigureMockMvc //handle the http request without calling server
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {QuartzTest.class})
@ExtendWith({SpringExtension.class})
@DatabaseSetup("/data.xml")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@SpringJUnitConfig(ScheduledConfig.class)
public class Team {


    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TeamService teamService;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private ObjectMapper objectMapper;




    @Test
    public void AddTeam() throws Exception {


        addTeamCommand teamCommand = new addTeamCommand(2, "sa3ka");

        String JSONTeam = objectMapper.writeValueAsString(teamCommand);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/teamController/adding")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123"))
                .contentType(MediaType.APPLICATION_JSON).content(JSONTeam))
                .andExpect(status().isOk())
                .andExpect(content().json(JSONTeam));

        Optional<com.example.Phase12.sections.Team> team = teamRepository.findById(2);

        Assertions.assertEquals(teamCommand.getIdTeam(), team.get().getIdTeam());
        Assertions.assertEquals(teamCommand.getTeamName(), teamCommand.getTeamName());


    }

    @Test
    public void AddTeamAlreadyExistTesting() throws Exception {

//        Team team1 = new Team();
//        team1.setTeamName("tiko");
//        team1.setIdTeam(1);
        addTeamCommandTeamIdException teamCommandTeamIdException = new addTeamCommandTeamIdException(1);

        String JSONTeam = objectMapper.writeValueAsString(teamCommandTeamIdException);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/teamController/adding")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123"))
                .contentType(MediaType.APPLICATION_JSON).content(JSONTeam))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BadArgumentsException))
                .andExpect(result -> Assertions.assertEquals("team with this id is added before!", result.getResolvedException().getMessage()));

    }

    @Test
    public void addTeamNameNotFoundTesting() throws Exception {


        addTeamCommand teamCommand = new addTeamCommand(2, null);

        String JSONTeam = objectMapper.writeValueAsString(teamCommand);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/teamController/adding")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123"))
                .contentType(MediaType.APPLICATION_JSON).content(JSONTeam))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> Assertions.assertEquals("The name is null!", result.getResolvedException().getMessage()));

    }

    @Test
    public void getTeam() throws Exception{

        this.mockMvc.perform(MockMvcRequestBuilders.get("/teamController/getTeam/1")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123")))
                .andExpect(status().isOk());

    }

    @Test
    public void getTeamButTeamIdNotFound() throws Exception{

        this.mockMvc.perform(MockMvcRequestBuilders.get("/teamController/getTeam/4")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123")))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> Assertions.assertEquals("team with this id is not found!", result.getResolvedException().getMessage()));

    }

    @Test
    public void getEmployeesInTeams() throws Exception {

        Optional<com.example.Phase12.sections.Team> team = teamRepository.findById(1);


        this.mockMvc.perform(MockMvcRequestBuilders.get("/teamController/gettingEmployeesUnderTeam/1")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("joo", "manager123"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(team.get().getIdTeam())))
                .andExpect(status().isOk());

        Optional<com.example.Phase12.sections.Team> teamToCheckWith = teamRepository.findById(1);
        Assertions.assertEquals(team.get().getIdTeam(), teamToCheckWith.get().getIdTeam());
        Assertions.assertEquals(team.get().getTeamName(), teamToCheckWith.get().getTeamName());

    }

    @Test
    public void getEmployeesInTeamsTeamNotFoundTesting() throws Exception {

        Optional<com.example.Phase12.sections.Team> team = teamRepository.findById(1);

        List<Employee> employees = teamService.EmployeesUnderTeam(team.get().getIdTeam());


        String jsonEmployees = objectMapper.writeValueAsString(employees);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/teamController/gettingEmployeesUnderTeam/5")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("joo", "manager123"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(team.get().getIdTeam())))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> Assertions.assertEquals("team with this id is not found!", result.getResolvedException().getMessage()));

    }

}
