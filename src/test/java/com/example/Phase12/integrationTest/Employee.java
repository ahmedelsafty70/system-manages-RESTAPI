package com.example.Phase12.integrationTest;

import com.example.Phase12.QuartzTest;
import com.example.Phase12.ScheduledConfig;
import com.example.Phase12.commands.employee.*;
import com.example.Phase12.exceptions.BadArgumentsException;
import com.example.Phase12.exceptions.ResourceNotFoundException;
import com.example.Phase12.repository.*;
import com.example.Phase12.sections.DegreeEnum;
import com.example.Phase12.sections.Gender;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Optional;

import static com.example.Phase12.sections.DegreeEnum.Senior;
import static com.example.Phase12.sections.Gender.Male;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc //handle the http request without calling server
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {QuartzTest.class})
@ExtendWith({SpringExtension.class})
@DatabaseSetup("/data.xml")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@SpringJUnitConfig(ScheduledConfig.class)
public class Employee {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Test
    public void addEmployee() throws Exception {

        addEmployeeCommand employeeCommand = new addEmployeeCommand(10, "mohamed", "HR", "18105254", "ay7aga"
                , passwordEncoder.encode("hr123"), 1, 12, 15f, Gender.Male, DegreeEnum.Senior);

        String employee = objectMapper.writeValueAsString(employeeCommand);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/HumanResources/employees/add")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123"))
                .contentType(MediaType.APPLICATION_JSON).content(employee))
                .andDo(print()).andExpect(status().isOk());

    }

    @Test
    public void addEmployeeForbiddenException() throws Exception {

        addEmployeeCommand employeeCommand = new addEmployeeCommand(10, "mohamed", "HR", "18105254", "ay7aga"
                , passwordEncoder.encode("hr123"), 1, 12, 15f,Male,Senior);

        String employee = objectMapper.writeValueAsString(employeeCommand);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/HumanResources/employees/add")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("safty", "safty123"))
                .contentType(MediaType.APPLICATION_JSON).content(employee))
                .andDo(print()).andExpect(status().isForbidden());

    }


    @Test
    public void addEmployeeSecondNameNotFoundTesting() throws Exception {

        addEmployeeCommandSecondNameException employeeCommandSecondNameException = new addEmployeeCommandSecondNameException(4, null);

        String employee = objectMapper.writeValueAsString(employeeCommandSecondNameException);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/HumanResources/employees/add")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123"))
                .contentType(MediaType.APPLICATION_JSON).content(employee))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(result -> Assertions.assertEquals("The second_name is null", result.getResolvedException().getMessage()));

    }

    @Test
    public void addEmployeeNationalIdNotFoundTesting() throws Exception {

        addEmployeeCommandNationalidException addEmployeeCommandNationalidException = new addEmployeeCommandNationalidException(4, null, "mohamed");

        String employee = objectMapper.writeValueAsString(addEmployeeCommandNationalidException);


        this.mockMvc.perform(MockMvcRequestBuilders.post("/HumanResources/employees/add")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123"))
                .contentType(MediaType.APPLICATION_JSON).content(employee))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> Assertions.assertEquals("The national_id is null", result.getResolvedException().getMessage()));

    }

    @Test
    public void addEmployeeNationalIdInvalidTesting() throws Exception {

        addEmployeeCommandNationalidException addEmployeeCommandNationalidException = new addEmployeeCommandNationalidException(4, "-18105254", "mohamed");

        String employee = objectMapper.writeValueAsString(addEmployeeCommandNationalidException);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/HumanResources/employees/add")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123"))
                .contentType(MediaType.APPLICATION_JSON).content(employee))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BadArgumentsException))
                .andExpect(result -> Assertions.assertEquals("INVALID national-id", result.getResolvedException().getMessage()));

    }

    @Test
    public void addEmployeeGrossSalaryNotFoundTesting() throws Exception {

        addEmployeeCommandGrossSalaryException employeeCommandGrossSalaryException = new addEmployeeCommandGrossSalaryException(4, "mohamed", "18105254", null);

        String employee = objectMapper.writeValueAsString(employeeCommandGrossSalaryException);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/HumanResources/employees/add")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123"))
                .contentType(MediaType.APPLICATION_JSON).content(employee))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> Assertions.assertEquals("The gross salary is null", result.getResolvedException().getMessage()));

    }

    @Test
    public void addEmployeeActiveNotFoundTesting() throws Exception {

        addEmployeeCommandActiveException employeeCommandActiveException = new addEmployeeCommandActiveException(4,  "mohamed", "18105254", null,54f);

        String employee = objectMapper.writeValueAsString(employeeCommandActiveException);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/HumanResources/employees/add")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123"))
                .contentType(MediaType.APPLICATION_JSON).content(employee))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> Assertions.assertEquals("Employee.getActive is null", result.getResolvedException().getMessage()));

    }



    @Test
    public void addEmployeeUsernameNotFoundTesting() throws Exception {

        addEmployeeCommandUsernameException addEmployeeCommandUsernameException = new addEmployeeCommandUsernameException(4, "mohamed", "18105254", null, 1,54f);

        String employee = objectMapper.writeValueAsString(addEmployeeCommandUsernameException);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/HumanResources/employees/add")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123"))
                .contentType(MediaType.APPLICATION_JSON).content(employee))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> Assertions.assertEquals("Employee.username is null", result.getResolvedException().getMessage()));

    }

    @Test
    public void addEmployeePasswordNotFoundTesting() throws Exception {

        addEmployeeCommandPasswordException addEmployeeCommandPasswordException = new addEmployeeCommandPasswordException(4, "mohamed", "18105254", "Username", 1,54f, null);

        String employee = objectMapper.writeValueAsString(addEmployeeCommandPasswordException);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/HumanResources/employees/add")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123"))
                .contentType(MediaType.APPLICATION_JSON).content(employee))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> Assertions.assertEquals("Employee.password is null", result.getResolvedException().getMessage()));

    }

    @Test
    public void addEmployeeYearsOfExperienceNotFoundTesting() throws Exception {

        addEmployeeCommandYearsOfExperienceException addEmployeeCommandYearsOfExperienceException = new addEmployeeCommandYearsOfExperienceException(4, "mohamed", "18105254", "Username", 1,54f, "safty123",null);

        String employee = objectMapper.writeValueAsString(addEmployeeCommandYearsOfExperienceException);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/HumanResources/employees/add")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123"))
                .contentType(MediaType.APPLICATION_JSON).content(employee))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> Assertions.assertEquals("Employee.years_of_experience is null", result.getResolvedException().getMessage()));

    }


    @Test
    public void addEmployeeRoleNotFoundTesting() throws Exception {

        addEmployeeCommandRolesException addEmployeeCommandRolesException = new addEmployeeCommandRolesException(4, "mohamed", "18105254", "Username", 1,54f, "safty123","234",null);

        String employee = objectMapper.writeValueAsString(addEmployeeCommandRolesException);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/HumanResources/employees/add")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123"))
                .contentType(MediaType.APPLICATION_JSON).content(employee))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> Assertions.assertEquals("Employee.roles is null", result.getResolvedException().getMessage()));

    }

    @Test
    public void DeletingEmployeeAndManagers() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/HumanResources/employees/deleting/2")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123")))
                .andExpect(status().isOk());
        Assertions.assertEquals(employeeRepository.existsById(2), false);

    }

    @Test
    public void DeletingEmployeeAndManagersForbiddenException() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/HumanResources/employees/deleting/2")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("safty", "safty123")))
                .andExpect(status().isForbidden());

    }

    @Test
    public void DeletingEmployeeAndManagersNotFoundTesting() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/HumanResources/employees/deleting/9")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123")))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> Assertions.assertEquals("employee with this id is not found!", result.getResolvedException().getMessage()));
        Assertions.assertEquals(employeeRepository.existsById(9), false);

    }

    @Test
    public void GettingEmployeeInfoForHRUsing() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/HumanResources/employees/get/gettingEmployee/1")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123")))
                .andExpect(status().isOk());
    }
    @Test
    public void GettingEmployeeInfoForHRandManagerUsing() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/HumanResources/employees/get/gettingEmployee/1")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("joo", "manager123")))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BadArgumentsException))
                .andExpect(result -> Assertions.assertEquals("The manager is not allowed to see this info!", result.getResolvedException().getMessage()));
    }

    @Test
    public void GettingEmployeeInfoForHRUsingForbiddenException() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/HumanResources/employees/get/gettingEmployee/1")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("safty", "safty123")))
                .andExpect(status().isForbidden());
    }

    @Test
    public void GettingEmployeeInfoForUSERUsing() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/HumanResources/notForHR/gettingEmployee")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("safty", "safty123")))
                .andExpect(status().isOk());
    }
//    @Test
//    public void GettingEmployeeInfoForUSERUsing2() throws Exception {
//
//        this.mockMvc.perform(MockMvcRequestBuilders.get("/HumanResources/notForHR/gettingEmployee")
//                .with(SecurityMockMvcRequestPostProcessors.httpBasic("safty", "safty123")))
//                .andExpect(status().isBadRequest())
//                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BadArgumentsException))
//                .andExpect(result -> Assertions.assertEquals("The manager is not allowed to see this info!", result.getResolvedException().getMessage()));
//
//    }
    @Test
    public void GettingEmployeeInfoForUSERUsingForbiddenException() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/HumanResources/notForHR/gettingEmployee")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123")))
                .andExpect(status().isForbidden());
    }

    @Test
    public void GettingEmployeeInfoEmployeeNotFoundExceptionTesting() throws Exception {


        this.mockMvc.perform(MockMvcRequestBuilders.get("/HumanResources/employees/get/gettingEmployee/6")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123")))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> Assertions.assertEquals("employee with this id is not found!", result.getResolvedException().getMessage()));

    }

    @Test
    public void GettingEmployeeInfoUnauthenticatedExceptionForHRTesting() throws Exception{

        this.mockMvc.perform(MockMvcRequestBuilders.get("/HumanResources/employees/get/gettingEmployee/1")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("sasa", "hr123")));
    }

    @Test
    public void GettingEmployeeInfoUnauthenticatedExceptionForUSERTesting() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/HumanResources/notForHR/gettingEmployee")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("sasa", "safty123")));
    }


    @Test
    public void modify() throws Exception {

        addEmployeeCommand employeeCommand = new addEmployeeCommand();

        employeeCommand.setSecond_name("salwa");
        employeeCommand.setRoles("HR");

        int idOfThePersonToBeUpdated = 1;

        String JSONEmployee = objectMapper.writeValueAsString(employeeCommand);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/HumanResources/employees/updating/" + idOfThePersonToBeUpdated)
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123"))
                .contentType(MediaType.APPLICATION_JSON).content(JSONEmployee))
                .andExpect(status().isOk());

    }
    @Test
    public void modifyForbiddenException() throws Exception {

        addEmployeeCommand employeeCommand = new addEmployeeCommand();

        employeeCommand.setSecond_name("salwa");
        employeeCommand.setRoles("HR");

        int idOfThePersonToBeUpdated = 1;

        String JSONEmployee = objectMapper.writeValueAsString(employeeCommand);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/HumanResources/employees/updating/" + idOfThePersonToBeUpdated)
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("safty", "safty123"))
                .contentType(MediaType.APPLICATION_JSON).content(JSONEmployee))
                .andExpect(status().isForbidden());

    }

    @Test
    public void modifyNotFindEmployeeTesting() throws Exception {

        addEmployeeCommand employeeCommand = new addEmployeeCommand();

        employeeCommand.setSecond_name("salwa");
        employeeCommand.setRoles("HR");

        int idOfThePersonToBeUpdated = 9;

        String JSONEmployee = objectMapper.writeValueAsString(employeeCommand);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/HumanResources/employees/updating/" + idOfThePersonToBeUpdated)
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123"))
                .contentType(MediaType.APPLICATION_JSON).content(JSONEmployee))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> Assertions.assertEquals("employee with this id is not found!", result.getResolvedException().getMessage()));


    }


    @Test
    public void GettingSalaryInfoOfMine() throws Exception {


        this.mockMvc.perform(MockMvcRequestBuilders.get("/HumanResources/notForHR/gettingSalary")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("safty", "safty123")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

    }

    @Test
    public void GettingSalaryInfoOfMineForbiddenException() throws Exception {


        this.mockMvc.perform(MockMvcRequestBuilders.get("/HumanResources/notForHR/gettingSalary")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isForbidden());

    }



    @Test
    public void GettingSalaryInfoHR() throws Exception {

        int EmployeeId = 1;

        this.mockMvc.perform(MockMvcRequestBuilders.get("/HumanResources/employees/get/gettingSalary/" + EmployeeId)
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    public void GettingSalaryInfoHRForbiddenException() throws Exception {

        int EmployeeId = 1;

        this.mockMvc.perform(MockMvcRequestBuilders.get("/HumanResources/employees/gettingSalary/" + EmployeeId)
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("safty", "safty123")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void GettingSalaryInfoHRUsingNotFoundException() throws Exception{
        int EmployeeId = 7;

        this.mockMvc.perform(MockMvcRequestBuilders.get("/HumanResources/employees/get/gettingSalary/" + EmployeeId)
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> Assertions.assertEquals("employee with this id is not found!", result.getResolvedException().getMessage()));

    }

    @Test
    public void GettingSalaryInfoUnauthenticatedExceptionForHRTesting() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/HumanResources/employees/gettingSalary")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("dada", "hr123")));

    }

    @Test
    public void GettingSalaryInfoUnauthenticatedExceptionForUserTesting() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/HumanResources/notForHR/gettingSalary")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("baba", "hr123")));

    }

    @Test
    public void GettingEmployeesOfManager() throws Exception {

        Optional<com.example.Phase12.sections.Employee> manager = employeeRepository.findById(1);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/HumanResources/employees/get/gettingUnderEmployees/1")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123")))
                .andExpect(status().isOk());

        com.example.Phase12.sections.Employee managerForChecking = employeeRepository.getById(1);

        Assertions.assertEquals(manager.get().getIdEmployee(), managerForChecking.getIdEmployee());
    }

    @Test
    public void GettingEmployeesOfManagerForbiddenException() throws Exception {

        Optional<com.example.Phase12.sections.Employee> manager = employeeRepository.findById(1);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/HumanResources/employees/gettingUnderEmployees/1")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("safty", "safty123")))
                .andExpect(status().isForbidden());


    }

    @Test
    public void GettingEmployeesOfManagerTesting() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/HumanResources/employees/get/gettingUnderEmployees/4")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123")))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> Assertions.assertEquals("manager with this id is not found!", result.getResolvedException().getMessage()));


    }

    @Test
    public void GettingEmployeesRecursively() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/HumanResources/employees/get/gettingEmployeesRecursively/2")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123")))
                .andExpect(status().isOk());


    }

    @Test
    public void GettingEmployeesRecursivelyForbiddenException() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/HumanResources/employees/gettingEmployeesRecursively/2")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("safty", "safty123")))
                .andExpect(status().isForbidden());


    }
    @Test
    public void GettingEmployeesRecursivelyIdNotFound() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/HumanResources/employees/get/gettingEmployeesRecursively/8")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123")))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> Assertions.assertEquals("The manager with this id not found!", result.getResolvedException().getMessage()));



    }




}
