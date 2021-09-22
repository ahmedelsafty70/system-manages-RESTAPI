package com.example.Phase12.integrationTest;

import com.example.Phase12.QuartzTest;
import com.example.Phase12.ScheduledConfig;
import com.example.Phase12.commands.vacation.*;
import com.example.Phase12.controller.DepartmentController;
import com.example.Phase12.exceptions.BadArgumentsException;
import com.example.Phase12.exceptions.ResourceNotFoundException;
import com.example.Phase12.repository.*;
import com.example.Phase12.sections.Employee;
import com.example.Phase12.service.EmployeeService;
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
import org.springframework.security.crypto.password.PasswordEncoder;
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
public class Vacation {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private VacationRepository vacationRepository;


    @Test
    public void addingVacation() throws Exception {

        addVacationCommand vacationCommand = new addVacationCommand(5, "SASA", 2021, 1); //##ASK AMIN for the employee

        String JSONVacation = objectMapper.writeValueAsString(vacationCommand);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/VacationController/addVacation")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123"))
                .contentType(MediaType.APPLICATION_JSON).content(JSONVacation))
                .andExpect(status().isOk()).andExpect(content().json(JSONVacation));

    }

    @Test
    public void addingVacationForbiddenException() throws Exception {

       // com.example.Phase12.sections.Employee employee = employeeRepository.findById(1).orElse(null);

        addVacationCommand vacationCommand = new addVacationCommand(5, "SASA", 2021, '1'); //##ASK AMIN for the employee

        String JSONVacation = objectMapper.writeValueAsString(vacationCommand);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/VacationController/addVacation")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("safty", "safty123"))
                .contentType(MediaType.APPLICATION_JSON).content(JSONVacation))
                .andExpect(status().isForbidden());

    }



    @Test
    public void addingVacationVacationAlreadyExistTesting() throws Exception {


        addVacationCommandVacationIdException vacationCommandVacationIdException = new addVacationCommandVacationIdException(1); //##ASK AMIN for the employee

        String JSONVacation = objectMapper.writeValueAsString(vacationCommandVacationIdException);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/VacationController/addVacation")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123"))
                .contentType(MediaType.APPLICATION_JSON).content(JSONVacation))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BadArgumentsException))
                .andExpect(result -> Assertions.assertEquals("vacation with this id is added before!", result.getResolvedException().getMessage()));

    }

    @Test
    public void addingVacationNameOfEmployeeNotFoundTesting() throws Exception {

        addVacationCommandEmployeeNameException vacationCommandEmployeeNameException = new addVacationCommandEmployeeNameException(5, null); //##ASK AMIN for the employee

        String JSONVacation = objectMapper.writeValueAsString(vacationCommandEmployeeNameException);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/VacationController/addVacation")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123"))
                .contentType(MediaType.APPLICATION_JSON).content(JSONVacation))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> Assertions.assertEquals("The name of the employee is null!", result.getResolvedException().getMessage()));

    }

    @Test
    public void addingVacationYearInTheFutureIsInvalidTesting() throws Exception {


        addVacationCommandYearExceedingException vacationCommandYearExceedingException = new addVacationCommandYearExceedingException(5, "sasa", 2023); //##ASK AMIN for the employee

        String JSONVacation = objectMapper.writeValueAsString(vacationCommandYearExceedingException);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/VacationController/addVacation")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123"))
                .contentType(MediaType.APPLICATION_JSON).content(JSONVacation))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> Assertions.assertEquals("This year is invalid! This year didn't come yet.", result.getResolvedException().getMessage()));
    }

    @Test
    public void addingVacationYearBeforeJoiningInvalidTesting() throws Exception {


        //Employee employee = employeeRepository.findById(1).orElse(null);
        addVacationCommandYearBeforeJoiningException vacationCommandYearException = new addVacationCommandYearBeforeJoiningException(5, "sasa", 199, 1); //##ASK AMIN for the employee

        String JSONVacation = objectMapper.writeValueAsString(vacationCommandYearException);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/VacationController/addVacation")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123"))
                .contentType(MediaType.APPLICATION_JSON).content(JSONVacation))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> Assertions.assertEquals("This year is invalid! choose a year after you joined our community.", result.getResolvedException().getMessage()));
    }


    @Test
    public void gettingVacation() throws Exception {
        Optional<com.example.Phase12.sections.Vacation> vacation = vacationRepository.findById(1);

        String JSONVacation = objectMapper.writeValueAsString(vacation.get());
        mockMvc.perform(MockMvcRequestBuilders.get("/VacationController/get/1")

                .with(SecurityMockMvcRequestPostProcessors.httpBasic("joo", "manager123")))
                // .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123"))
                .andExpect(status().isOk());

        Optional<com.example.Phase12.sections.Vacation> vacationResult = vacationRepository.findById(1);

        Assertions.assertEquals(vacation.get().getId(), vacationResult.get().getId());

    }

    @Test
    public void gettingVacationForbiddenException() throws Exception {
        Optional<com.example.Phase12.sections.Vacation> vacation = vacationRepository.findById(1);

        String JSONVacation = objectMapper.writeValueAsString(vacation.get());
        mockMvc.perform(MockMvcRequestBuilders.get("/VacationController/get/1")

                .with(SecurityMockMvcRequestPostProcessors.httpBasic("safty", "safty123")))
                .andExpect(status().isForbidden());

    }



    @Test
    public void gettingVacationNotFoundTesting() throws Exception {
        Optional<com.example.Phase12.sections.Vacation> vacation = vacationRepository.findById(1);

        String JSONVacation = objectMapper.writeValueAsString(vacation.get());
        mockMvc.perform(MockMvcRequestBuilders.get("/VacationController/get/5")

                .with(SecurityMockMvcRequestPostProcessors.httpBasic("joo", "manager123")))
                // .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123"))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> Assertions.assertEquals("vacation with this id doesn't exist!", result.getResolvedException().getMessage()));


    }


}
