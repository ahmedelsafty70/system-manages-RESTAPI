package com.example.Phase12.integrationTest;


import com.example.Phase12.QuartzTest;
import com.example.Phase12.ScheduledConfig;
import com.example.Phase12.commands.earning.addEarningCommand;
import com.example.Phase12.exceptions.BadArgumentsException;
import com.example.Phase12.repository.EmployeeRepository;
import com.example.Phase12.security.BaseController;
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
public class Earnings extends BaseController {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void addEarning() throws Exception{

        int employeeId = 1;

        addEarningCommand earningCommand = new addEarningCommand(23f,45f);
        String JSONEarning = objectMapper.writeValueAsString(earningCommand);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/HumanResources/Earning/add/" + employeeId)
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123"))
                .contentType(MediaType.APPLICATION_JSON).content(JSONEarning))
                .andExpect(status().isOk());

    }
    @Test
    public void addEarningForbiddenException() throws Exception{

        int employeeId = 1;

        addEarningCommand earningCommand = new addEarningCommand(23f,45f);
        String JSONEarning = objectMapper.writeValueAsString(earningCommand);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/HumanResources/Earning/add/" + employeeId)
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("safty", "safty123"))
                .contentType(MediaType.APPLICATION_JSON).content(JSONEarning))
                .andExpect(status().isForbidden());

    }

    @Test
    public void addEarningButInputWouldNotMakeAChange() throws Exception{

        int employeeId = 1;

        addEarningCommand earningCommand = new addEarningCommand(0f,0f);
        String JSONEarning = objectMapper.writeValueAsString(earningCommand);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/HumanResources/Earning/add/" + employeeId)
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123"))
                .contentType(MediaType.APPLICATION_JSON).content(JSONEarning))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BadArgumentsException))
                .andExpect(result -> Assertions.assertEquals("You did not request any changes", result.getResolvedException().getMessage()));
    }

    @Test
    public void getListOfEarningOfAnEmployeeSinceHeJoined() throws Exception{

        int employeeId = 1;

        this.mockMvc.perform(MockMvcRequestBuilders.get("/HumanResources/Earning/get/" + employeeId)
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123")))
                .andExpect(status().isOk());
    }
    @Test
    public void getListOfEarningOfAnEmployeeSinceHeJoinedForbiddenException() throws Exception{

        int employeeId = 1;

        this.mockMvc.perform(MockMvcRequestBuilders.get("/HumanResources/Earning/get/" + employeeId)
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("safty", "safty123")))
                .andExpect(status().isForbidden());
    }

}
