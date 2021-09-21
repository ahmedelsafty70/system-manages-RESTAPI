package com.example.Phase12.integrationTest;

import com.example.Phase12.QuartzTest;
import com.example.Phase12.ScheduledConfig;
import com.example.Phase12.commands.salaryDetails.addSalaryDetailsCommand;
import com.example.Phase12.commands.salaryDetails.addSalaryDetailsCommandActualSalaryException;
import com.example.Phase12.commands.salaryDetails.addSalaryDetailsCommandDateException;
import com.example.Phase12.commands.salaryDetails.addSalaryDetailsCommandSalaryDetailsIdException;
import com.example.Phase12.exceptions.BadArgumentsException;
import com.example.Phase12.exceptions.ResourceNotFoundException;
import com.example.Phase12.repository.EmployeeRepository;
import com.example.Phase12.repository.SalaryDetailsRepository;
import com.example.Phase12.sections.Employee;
import com.example.Phase12.sections.SalaryDetails;
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
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.scheduling.config.ScheduledTaskHolder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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
public class salaryDetails {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SalaryDetailsRepository salaryDetailsRepository;

    @Autowired
    private ScheduledTaskHolder scheduledTaskHolder;

    @Test
    public void getSalaryDetails() throws Exception {

        int employeeId =1;
        List<SalaryDetails> salaryDetails = salaryDetailsRepository.salaryOfSpecificEmployee(employeeId);



        String JSONSalaryDetails = objectMapper.writeValueAsString(salaryDetails);
        mockMvc.perform(MockMvcRequestBuilders.get("/salaryController/getSalaryDetails/" + employeeId)
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("joo", "manager123")))
                // .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123"))
                .andExpect(status().isOk()).andExpect(content().json(JSONSalaryDetails));

    }

    @Test
    public void getSalaryDetailsForbiddenException() throws Exception {

        int employeeId =1;

        mockMvc.perform(MockMvcRequestBuilders.get("/salaryController/getSalaryDetails/" + employeeId)
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("safty", "safty123")))
                .andExpect(status().isForbidden());

    }


    @Test
    public void getSalaryDetailsNotFoundTesting() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/salaryController/getSalaryDetails/5")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("joo", "manager123")))
                // .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123"))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> Assertions.assertEquals("salary details with this id isn't found!", result.getResolvedException().getMessage()));

    }

    @Test
    @Transactional
    public void addSalaryDetails() throws Exception {

        Date date = new Date(2020, 8, 5);
        Employee employee = employeeRepository.findById(1).orElse(null);
        addSalaryDetailsCommand salaryDetailsCommand = new addSalaryDetailsCommand(3, 5000f, date, employee);

        String JSONSalaryDetails = objectMapper.writeValueAsString(salaryDetailsCommand);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/salaryController/addSalaryDetails")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123"))
                .contentType(MediaType.APPLICATION_JSON).content(JSONSalaryDetails))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void addSalaryDetailsForbiddenException() throws Exception {

        Date date = new Date(2020, 8, 5);
        Employee employee = employeeRepository.findById(1).orElse(null);
        addSalaryDetailsCommand salaryDetailsCommand = new addSalaryDetailsCommand(3, 5000f, date, employee);

        String JSONSalaryDetails = objectMapper.writeValueAsString(salaryDetailsCommand);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/salaryController/addSalaryDetails")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("safty", "safty123"))
                .contentType(MediaType.APPLICATION_JSON).content(JSONSalaryDetails))
                .andExpect(status().isForbidden());
    }

    @Test
    public void addSalaryDetailsIdAlreadyExistTesting() throws Exception {

        addSalaryDetailsCommandSalaryDetailsIdException salaryDetailsCommandSalaryDetailsIdException = new addSalaryDetailsCommandSalaryDetailsIdException(1);

        String JSONSalaryDetails = objectMapper.writeValueAsString(salaryDetailsCommandSalaryDetailsIdException);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/salaryController/addSalaryDetails")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123"))
                .contentType(MediaType.APPLICATION_JSON).content(JSONSalaryDetails))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BadArgumentsException))
                .andExpect(result -> Assertions.assertEquals("salary details with this id already exist!", result.getResolvedException().getMessage()));
    }

    @Test
    public void addSalaryDetailsActualSalaryInvalidTesting() throws Exception {


        addSalaryDetailsCommandActualSalaryException salaryDetailsCommandActualSalaryException = new addSalaryDetailsCommandActualSalaryException(3, -45f);

        String JSONSalaryDetails = objectMapper.writeValueAsString(salaryDetailsCommandActualSalaryException);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/salaryController/addSalaryDetails")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123"))
                .contentType(MediaType.APPLICATION_JSON).content(JSONSalaryDetails))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BadArgumentsException))
                .andExpect(result -> Assertions.assertEquals("actual salary cannot be less than zero!", result.getResolvedException().getMessage()));
    }

    @Test
    public void addSalaryDetailsDateNotFoundTesting() throws Exception {


        addSalaryDetailsCommandDateException salaryDetailsCommandDateException = new addSalaryDetailsCommandDateException(3, 1000f, null);

        String JSONSalaryDetails = objectMapper.writeValueAsString(salaryDetailsCommandDateException);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/salaryController/addSalaryDetails")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123"))
                .contentType(MediaType.APPLICATION_JSON).content(JSONSalaryDetails))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BadArgumentsException))
                .andExpect(result -> Assertions.assertEquals("date is null!", result.getResolvedException().getMessage()));
    }

    @Test
    public void testMonthlyCronTaskScheduled() {
        Set<ScheduledTask> scheduledTasks = scheduledTaskHolder.getScheduledTasks();
        scheduledTasks.forEach(scheduledTask -> scheduledTask.getTask().getRunnable().getClass().getDeclaredMethods());
        long count = scheduledTasks.stream()
                .filter(scheduledTask -> scheduledTask.getTask() instanceof CronTask)
                .map(scheduledTask -> (CronTask) scheduledTask.getTask())
                .filter(cronTask -> cronTask.getExpression().equals("0 0 0 25 * *") && cronTask.toString().equals("com.example.Phase12.service.SalaryDetailsService.generateSalaryAllEmployees"))
                .count();
        assertThat(count).isEqualTo(1L);
    }

}
