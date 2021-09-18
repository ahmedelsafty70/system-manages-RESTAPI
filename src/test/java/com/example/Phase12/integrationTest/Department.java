package com.example.Phase12.integrationTest;

import com.example.Phase12.QuartzTest;
import com.example.Phase12.ScheduledConfig;
import com.example.Phase12.commands.department.addDepartmentCommand;
import com.example.Phase12.commands.department.addDepartmentCommandDepartmentIdException;
import com.example.Phase12.exceptions.BadArgumentsException;
import com.example.Phase12.exceptions.ResourceNotFoundException;
import com.example.Phase12.repository.*;
import com.example.Phase12.sections.Team;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles("test")
@AutoConfigureMockMvc //handle the http request without calling server
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {QuartzTest.class})
@ExtendWith({SpringExtension.class})
@DatabaseSetup("/data.xml")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@SpringJUnitConfig(ScheduledConfig.class)
public class Department {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private TeamRepository teamRepository;


    @Autowired
    private ObjectMapper objectMapper;




    @Test
    public void addDepartment() throws Exception {


        addDepartmentCommand departmentCommand = new addDepartmentCommand(45, "Computer");

        String JSONDepartment = objectMapper.writeValueAsString(departmentCommand);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/departmentController/adding")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123"))
                .contentType(MediaType.APPLICATION_JSON).content(JSONDepartment))
                .andExpect(status().isOk());

        Optional<com.example.Phase12.sections.Department> department = departmentRepository.findById(45);     // ##### ASK AMIN USED IT OR NOT
        Assertions.assertEquals(departmentCommand.getId(), department.get().getId());
        Assertions.assertEquals(departmentCommand.getName(), department.get().getName());
    }
    @Test
    public void addDepartmentForbiddenException() throws Exception {


        addDepartmentCommand departmentCommand = new addDepartmentCommand(45, "Computer");

        String JSONDepartment = objectMapper.writeValueAsString(departmentCommand);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/departmentController/adding")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("safty", "safty123"))
                .contentType(MediaType.APPLICATION_JSON).content(JSONDepartment))
                .andExpect(status().isForbidden());
    }
    @Test
    public void addDepartmentAlreadyExistExceptionTesting() throws Exception {


        addDepartmentCommandDepartmentIdException departmentCommandDepartmentIdException = new addDepartmentCommandDepartmentIdException(1);

        String JSONDepartment = objectMapper.writeValueAsString(departmentCommandDepartmentIdException);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/departmentController/adding")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123"))
                .contentType(MediaType.APPLICATION_JSON).content(JSONDepartment))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BadArgumentsException))
                .andExpect(result -> Assertions.assertEquals("department with this id is added before!", result.getResolvedException().getMessage()));

    }

    @Test
    public void addDepartmentNameNotFoundTesting() throws Exception {


        addDepartmentCommand departmentCommand = new addDepartmentCommand(45, null);

        String JSONDepartment = objectMapper.writeValueAsString(departmentCommand);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/departmentController/adding")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123"))
                .contentType(MediaType.APPLICATION_JSON).content(JSONDepartment))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> Assertions.assertEquals("The name is null!", result.getResolvedException().getMessage()));

    }

    @Test
    public void getDepartment() throws Exception{

        Optional<Team> team = teamRepository.findById(1);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/departmentController/GetDep/1")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123")))
                .andExpect(status().isOk());

        Optional<Team> comparingTeam = teamRepository.findById(1);

        Assertions.assertEquals(team.get().getTeamName(),comparingTeam.get().getTeamName());

    }
    @Test
    public void getDepartmentForbiddenException() throws Exception{

        Optional<Team> team = teamRepository.findById(1);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/departmentController/GetDep/1")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("safty", "safty123")))
                .andExpect(status().isForbidden());


    }



    @Test
    public void getDepartmentDepartmentNotFoundTesting() throws Exception{

        this.mockMvc.perform(MockMvcRequestBuilders.get("/departmentController/GetDep/5")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123")))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> Assertions.assertEquals("The department with this id not found!", result.getResolvedException().getMessage()));

    }
}
