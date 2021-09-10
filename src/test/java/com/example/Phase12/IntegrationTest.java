package com.example.Phase12;

import com.example.Phase12.commands.*;
import com.example.Phase12.exceptions.BadArgumentsException;
import com.example.Phase12.exceptions.ResourceNotFoundException;
import com.example.Phase12.repository.*;
import com.example.Phase12.sections.*;
import com.example.Phase12.controller.DepartmentController;
import com.example.Phase12.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import javassist.NotFoundException;
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
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.lang.String.format;
import static org.hamcrest.Matchers.is;
import static org.dbunit.Assertion.assertEquals;
import static org.dbunit.Assertion.assertEqualsIgnoreCols;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@AutoConfigureMockMvc //handle the http request without calling server
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {Application.class})
@ExtendWith({SpringExtension.class})
@DatabaseSetup("/data.xml")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
public class IntegrationTest {

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

    @Autowired
    private VacationRepository vacationRepository;

    @Autowired
    private SalaryDetailsRepository salaryDetailsRepository;


    @Test      //DONEEEEEEE
    public void addEmployee() throws Exception {

        addEmployeeCommand employeeCommand = new addEmployeeCommand(10, "mohamed", "HR", "18105254", "ay7aga", "hr123", 1, 12, 15f);

        String employee = objectMapper.writeValueAsString(employeeCommand);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/HumanResources/employees/add")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123"))
                .contentType(MediaType.APPLICATION_JSON).content(employee))
                .andDo(print()).andExpect(status().isOk());

    }

    @Test      //DONEEEEEEE
    public void addEmployeeTesting() throws Exception {

        addEmployeeCommand employeeCommand = new addEmployeeCommand(4, null, "HR", "18105254", "ay7aga", "hr123", 1, 12, 15f);

        String employee = objectMapper.writeValueAsString(employeeCommand);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/HumanResources/employees/add")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123"))
                .contentType(MediaType.APPLICATION_JSON).content(employee))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(result -> Assertions.assertEquals("The second_name is null", result.getResolvedException().getMessage()));

    }


    @Test
    public void DeletingEmployeeAndManagers() throws Exception {  //Doneeeeeeeeeee

        mockMvc.perform(MockMvcRequestBuilders.delete("/HumanResources/employees/deleting/2")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123")))
                .andExpect(status().isOk());
        Assertions.assertEquals(employeeRepository.existsById(2), false);

    }

    @Test
    public void DeletingEmployeeAndManagersNotFoundTesting() throws Exception {  //Doneeeeeeeeeee

        mockMvc.perform(MockMvcRequestBuilders.delete("/HumanResources/employees/deleting/9")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123")))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> Assertions.assertEquals("employee with this id is not found!", result.getResolvedException().getMessage()));
        Assertions.assertEquals(employeeRepository.existsById(9), false);

    }


    @Test
    public void GettingEmployeeInfo() throws Exception {  //Doneeeeeeeeeee

        addEmployeeCommand employeeCommand = new addEmployeeCommand();
        employeeCommand.setIdEmployee(1);

        String Result = objectMapper.writeValueAsString(employeeCommand);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/HumanResources/employees/gettingEmployee")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123"))
                .contentType(MediaType.APPLICATION_JSON).content(Result))
                .andExpect(status().isOk()).andExpect(content().json(Result));
    }


    @Test
    public void GettingEmployeeInfoTesting() throws Exception {  //Doneeeeeee

        addEmployeeCommand employeeCommand = new addEmployeeCommand();
        employeeCommand.setIdEmployee(5);

        String Result = objectMapper.writeValueAsString(employeeCommand);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/HumanResources/employees/gettingEmployee")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123"))
                .contentType(MediaType.APPLICATION_JSON).content(Result))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> Assertions.assertEquals("employee with this id is not found!", result.getResolvedException().getMessage()));

    }


//    @Test
//    public void modify() throws Exception {  //Doneeeeeeeeeee   <ANA SHALT EL DTO MALHASH LAZMA>
//
//        addEmployeeCommand employeeCommand = new addEmployeeCommand();
//
//        employeeCommand.setSecond_name("salwa");
//        employeeCommand.setRoles("HR");
//
//        int idOfThePersonToBeUpdated = 1;
//
//        String JSONEmployee = objectMapper.writeValueAsString(employeeCommand);
//
//        try {
//            this.mockMvc.perform(MockMvcRequestBuilders.put("/HumanResources/employees/updating/" + idOfThePersonToBeUpdated)
//                    .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123"))
//                    .contentType(MediaType.APPLICATION_JSON).content(JSONEmployee))
//                    .andExpect(status().isOk()).andExpect(content().json(JSONEmployee));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }


    @Test
    public void GettingSalaryInfo() throws Exception {  //Doneeeeeeeeeeeee   <ASK AMIN>

        int idOfThePersonToBeSearched = 1;

        Employee employee = employeeRepository.findById(1).orElse(null);
        if (employee == null)
            throw new NotFoundException("didnt find this id");

        EmployeeDTO expectedDTO = EmployeeDTO.EmployeeDEOFunc(employee);

        ObjectMapper objectMapper = new ObjectMapper();
        String expectedDTOJSON = objectMapper.writeValueAsString(expectedDTO);

        System.out.println(">>>>>>>>>>>>>>" + expectedDTOJSON);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/HumanResources/employees/gettingSalary/" + idOfThePersonToBeSearched)
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

    }

    @Test
    public void GettingSalaryInfoTesting() throws Exception {  //Doneeeeeeeeeeeee

        int idOfThePersonToBeSearched = 5;

        Employee employee = employeeRepository.findById(1).orElse(null);
        if (employee == null)
            throw new NotFoundException("didnt find this id");

        EmployeeDTO expectedDTO = EmployeeDTO.EmployeeDEOFunc(employee);

        ObjectMapper objectMapper = new ObjectMapper();
        String expectedDTOJSON = objectMapper.writeValueAsString(expectedDTO);

        System.out.println(">>>>>>>>>>>>>>" + expectedDTOJSON);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/HumanResources/employees/gettingSalary/" + idOfThePersonToBeSearched)
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> Assertions.assertEquals("employee with this id is not found!", result.getResolvedException().getMessage()));

    }



    @Test
    public void addDepartment() throws Exception {  //Doneeeeeeeeeeeeeeeeee


        addDepartmentCommand departmentCommand = new addDepartmentCommand(45, "Computer");

        String JSONDepartment = objectMapper.writeValueAsString(departmentCommand);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/departmentController/adding")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123"))
                .contentType(MediaType.APPLICATION_JSON).content(JSONDepartment))
                .andExpect(status().isOk());

//        Optional<Department> department = departmentRepository.findById(45);      ##### ASK AMIN USED IT OR NOT
//        Assertions.assertEquals(departmentCommand.getId(), department.get().getId());
//        Assertions.assertEquals(departmentCommand.getName(), department.get().getName());
    }
    @Test
    public void addDepartmentTesting1() throws Exception {  //Doneeeeeeeeeeeeeeeeee


        addDepartmentCommand departmentCommand = new addDepartmentCommand(1, "Computer");

        String JSONDepartment = objectMapper.writeValueAsString(departmentCommand);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/departmentController/adding")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123"))
                .contentType(MediaType.APPLICATION_JSON).content(JSONDepartment))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BadArgumentsException))
                .andExpect(result -> Assertions.assertEquals("department with this id is added before!", result.getResolvedException().getMessage()));

//        Optional<Department> department = departmentRepository.findById(45);      ##### ASK AMIN USED IT OR NOT
//        Assertions.assertEquals(departmentCommand.getId(), department.get().getId());
//        Assertions.assertEquals(departmentCommand.getName(), department.get().getName());
    }

    @Test
    public void addDepartmentTesting2() throws Exception {  //Doneeeeeeeeeeeeeeeeee


        addDepartmentCommand departmentCommand = new addDepartmentCommand(45, null);

        String JSONDepartment = objectMapper.writeValueAsString(departmentCommand);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/departmentController/adding")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123"))
                .contentType(MediaType.APPLICATION_JSON).content(JSONDepartment))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> Assertions.assertEquals("The name is null!", result.getResolvedException().getMessage()));
//        Optional<Department> department = departmentRepository.findById(45);      ##### ASK AMIN USED IT OR NOT
//        Assertions.assertEquals(departmentCommand.getId(), department.get().getId());
//        Assertions.assertEquals(departmentCommand.getName(), department.get().getName());
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
    public void getDepartmentTesting() throws Exception{

        this.mockMvc.perform(MockMvcRequestBuilders.get("/departmentController/GetDep/5")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123")))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> Assertions.assertEquals("The department with this id not found!", result.getResolvedException().getMessage()));



    }


    @Test
    public void AddTeam() throws Exception {  //Doneeeeeeeeeeeeeeee


        addTeamCommand teamCommand = new addTeamCommand(2, "sa3ka");

        String JSONTeam = objectMapper.writeValueAsString(teamCommand);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/teamController/adding")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123"))
                .contentType(MediaType.APPLICATION_JSON).content(JSONTeam))
                .andExpect(status().isOk())
                .andExpect(content().json(JSONTeam));

//        Optional<Team> team = teamRepository.findById(1);       ASK AMIN
//
//        Assertions.assertEquals(teamCommand.getIdTeam(), team.get().getIdTeam());
//        Assertions.assertEquals(teamCommand.getTeamName(), teamCommand.getTeamName());


    }

    @Test
    public void AddTeamTesting1() throws Exception {  //Doneeeeeeeeeeeeeeee

//        Team team1 = new Team();
//        team1.setTeamName("tiko");
//        team1.setIdTeam(1);
        addTeamCommand teamCommand = new addTeamCommand(1, "sa3ka");

        String JSONTeam = objectMapper.writeValueAsString(teamCommand);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/teamController/adding")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123"))
                .contentType(MediaType.APPLICATION_JSON).content(JSONTeam))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BadArgumentsException))
                .andExpect(result -> Assertions.assertEquals("team with this id is added before!", result.getResolvedException().getMessage()));


//        Optional<Team> team = teamRepository.findById(1);       ASK AMIN
//
//        Assertions.assertEquals(teamCommand.getIdTeam(), team.get().getIdTeam());
//        Assertions.assertEquals(teamCommand.getTeamName(), teamCommand.getTeamName());


    }

    @Test
    public void AddTeamTesting2() throws Exception {  //Doneeeeeeeeeeeeeeee


        addTeamCommand teamCommand = new addTeamCommand(2, null);

        String JSONTeam = objectMapper.writeValueAsString(teamCommand);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/teamController/adding")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123"))
                .contentType(MediaType.APPLICATION_JSON).content(JSONTeam))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> Assertions.assertEquals("The name is null!", result.getResolvedException().getMessage()));

//        Optional<Team> team = teamRepository.findById(1);       ASK AMIN
//
//        Assertions.assertEquals(teamCommand.getIdTeam(), team.get().getIdTeam());
//        Assertions.assertEquals(teamCommand.getTeamName(), teamCommand.getTeamName());
    }


    @Test
    public void getEmployeesInTeams() throws Exception { //Doneeeeeeeeeeeeeeeeee   AMIN CANNOT DO IT

        Optional<Team> team = teamRepository.findById(1);

        List<Employee> employees = teamService.EmployeesUnderTeam(team.get().getIdTeam());


        this.mockMvc.perform(MockMvcRequestBuilders.get("/teamController/gettingEmployeesUnderTeam/1")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("joo", "manager123"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(team.get().getIdTeam())))
                .andExpect(status().isOk());

        Optional<Team> teamToCheckWith = teamRepository.findById(1);
        Assertions.assertEquals(team.get().getIdTeam(), teamToCheckWith.get().getIdTeam());
        Assertions.assertEquals(team.get().getTeamName(), teamToCheckWith.get().getTeamName());

    }

    @Test
    public void getEmployeesInTeamsTesting() throws Exception { //Doneeeeeeeeeeeeeeeeee   AMIN CANNOT DO IT

        Optional<Team> team = teamRepository.findById(1);

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



    @Test //Doneeeeeeeeeeeeee
    public void GettingEmployeesOfManager() throws Exception {

        Optional<Employee> manager = employeeRepository.findById(1);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/HumanResources/employees/gettingUnderEmployees/1")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123")))
                .andExpect(status().isOk());

        Employee managerForChecking = employeeRepository.getById(1);

        Assertions.assertEquals(manager.get().getIdEmployee(), managerForChecking.getIdEmployee());
    }

    @Test //Doneeeeeeeeeeeeee
    public void GettingEmployeesOfManagerTesting() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/HumanResources/employees/gettingUnderEmployees/4")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123")))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> Assertions.assertEquals("employee with this id is not found!", result.getResolvedException().getMessage()));


    }

    @Test
    public void GettingEmployeesRecursively() throws Exception {
        List<Employee> employees = employeeRepository.getEmployeesUnderManagerRecursively(1);

        String jsonEmployees = objectMapper.writeValueAsString(employees);

        mockMvc.perform(MockMvcRequestBuilders.get("/HumanResources/employees/gettingEmployeesRecursively/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonEmployees));


    }

    @Test
    public void addingVacation() throws Exception { //Doneeeeeeeeeeeeee

        Optional<Employee> employee = employeeRepository.findById(1);



        addVacationCommand vacationCommand = new addVacationCommand(5, "SASA", 2000, 5, employee); //##ASK AMIN for the employee

        String JSONVacation = objectMapper.writeValueAsString(vacationCommand);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/VacationController/addVacation")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123"))
                .contentType(MediaType.APPLICATION_JSON).content(JSONVacation))
                .andExpect(status().isOk()).andExpect(status().isOk()).andExpect(content().json(JSONVacation));

    }
    @Test
    public void addingVacationTesting1() throws Exception { //Doneeeeeeeeeeeeee

        Optional<Employee> employee = employeeRepository.findById(1);



        addVacationCommand vacationCommand = new addVacationCommand(1, "SASA", 2000, 5, employee); //##ASK AMIN for the employee

        String JSONVacation = objectMapper.writeValueAsString(vacationCommand);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/VacationController/addVacation")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123"))
                .contentType(MediaType.APPLICATION_JSON).content(JSONVacation))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BadArgumentsException))
                .andExpect(result -> Assertions.assertEquals("vacation with this id is added before!", result.getResolvedException().getMessage()));


    }

    @Test
    public void addingVacationTesting2() throws Exception { //Doneeeeeeeeeeeeee

        Optional<Employee> employee = employeeRepository.findById(1);



        addVacationCommand vacationCommand = new addVacationCommand(5, null, 2000, 5, employee); //##ASK AMIN for the employee

        String JSONVacation = objectMapper.writeValueAsString(vacationCommand);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/VacationController/addVacation")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123"))
                .contentType(MediaType.APPLICATION_JSON).content(JSONVacation))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> Assertions.assertEquals("The name of the employee is null!", result.getResolvedException().getMessage()));


    }

    @Test
    public void addingVacationTesting3() throws Exception { //Doneeeeeeeeeeeeee

        Optional<Employee> employee = employeeRepository.findById(1);



        addVacationCommand vacationCommand = new addVacationCommand(5, "sasa", 0, 5, employee); //##ASK AMIN for the employee

        String JSONVacation = objectMapper.writeValueAsString(vacationCommand);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/VacationController/addVacation")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123"))
                .contentType(MediaType.APPLICATION_JSON).content(JSONVacation))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> Assertions.assertEquals("The year is null!", result.getResolvedException().getMessage()));
    }
    @Test
    public void addingVacationTesting4() throws Exception { //Doneeeeeeeeeeeeee

        Optional<Employee> employee = employeeRepository.findById(1);



        addVacationCommand vacationCommand = new addVacationCommand(5, "sasa", 2020, 0, employee); //##ASK AMIN for the employee

        String JSONVacation = objectMapper.writeValueAsString(vacationCommand);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/VacationController/addVacation")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123"))
                .contentType(MediaType.APPLICATION_JSON).content(JSONVacation))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> Assertions.assertEquals("No value present", result.getResolvedException().getMessage()));
    }

    @Test
    public void gettingVacation() throws Exception {
        Optional<Vacation> vacation = vacationRepository.findById(1);

        String JSONVacation = objectMapper.writeValueAsString(vacation.get());
        mockMvc.perform(MockMvcRequestBuilders.get("/VacationController/get/1")

                .with(SecurityMockMvcRequestPostProcessors.httpBasic("joo", "manager123")))
                // .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123"))
                .andExpect(status().isOk()).andExpect(content().json(JSONVacation));

        Optional<Vacation> vacationResult = vacationRepository.findById(1);

        Assertions.assertEquals(vacation.get().getId(), vacationResult.get().getId());

    }

    @Test
    public void gettingVacationTesting() throws Exception {
        Optional<Vacation> vacation = vacationRepository.findById(1);

        String JSONVacation = objectMapper.writeValueAsString(vacation.get());
        mockMvc.perform(MockMvcRequestBuilders.get("/VacationController/get/5")

                .with(SecurityMockMvcRequestPostProcessors.httpBasic("joo", "manager123")))
                // .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123"))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> Assertions.assertEquals("vacation with this id doesn't exist!", result.getResolvedException().getMessage()));


    }


    @Test
    public void getSalaryDetails() throws Exception {
        Optional<SalaryDetails> salaryDetails = salaryDetailsRepository.findById(1);

        String JSONSalaryDetails = objectMapper.writeValueAsString(salaryDetails.get());
        mockMvc.perform(MockMvcRequestBuilders.get("/salaryController/getSalaryDetails/1")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("joo", "manager123")))
                // .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123"))
                .andExpect(status().isOk()).andExpect(content().json(JSONSalaryDetails));

//        Optional<SalaryDetails> salaryDetailsForChecker = salaryDetailsRepository.findById(1);
//
//        Assertions.assertEquals(salaryDetails.get().getGrossSalary(),salaryDetailsForChecker.get().getGrossSalary());

    }

    @Test
    public void getSalaryDetailsTesting() throws Exception {

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
        SalaryDetails salaryDetails = new SalaryDetails();
        salaryDetails.setId(1);


        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        Date date = new Date(2020, 8, 5);
        //salaryDetails.setDate(date);

        Optional<Employee> employee = employeeRepository.findById(1);
        //salaryDetails.setEmployee(employee.get());
//        salaryDetails.setGrossSalary(45f);
//        salaryDetails.setNetSalary(78f);
//        salaryDetails.setBonus(87.5);
//        salaryDetails.setInsurance(47.1);
//        salaryDetails.setRaises(74.5);
        //       salaryDetails.setTaxes(58.9);

        addSalaryDetailsCommand salaryDetailsCommand = new addSalaryDetailsCommand(2, 5000f, date, employee);

        String JSONSalaryDetails = objectMapper.writeValueAsString(salaryDetailsCommand);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/salaryController/addSalaryDetails")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123"))
                .contentType(MediaType.APPLICATION_JSON).content(JSONSalaryDetails))
                .andExpect(status().isOk()).andExpect(status().isOk());


        Assertions.assertEquals(salaryDetails.getId(), salaryDetails.getId());
    }

    @Test
    @Transactional
    public void addSalaryDetailsTesting1() throws Exception {
        SalaryDetails salaryDetails = new SalaryDetails();
        salaryDetails.setId(1);


        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        Date date = new Date(2020, 8, 5);
        //salaryDetails.setDate(date);

        Optional<Employee> employee = employeeRepository.findById(1);
        //salaryDetails.setEmployee(employee.get());
//        salaryDetails.setGrossSalary(45f);
//        salaryDetails.setNetSalary(78f);
//        salaryDetails.setBonus(87.5);
//        salaryDetails.setInsurance(47.1);
//        salaryDetails.setRaises(74.5);
        //       salaryDetails.setTaxes(58.9);

        addSalaryDetailsCommand salaryDetailsCommand = new addSalaryDetailsCommand(1, 5000f, date, employee);

        String JSONSalaryDetails = objectMapper.writeValueAsString(salaryDetailsCommand);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/salaryController/addSalaryDetails")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123"))
                .contentType(MediaType.APPLICATION_JSON).content(JSONSalaryDetails))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BadArgumentsException))
                .andExpect(result -> Assertions.assertEquals("salary details with this id already exist!", result.getResolvedException().getMessage()));
    }

    @Test
    @Transactional
    public void addSalaryDetailsTesting2() throws Exception {
        SalaryDetails salaryDetails = new SalaryDetails();
        salaryDetails.setId(1);


        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        Date date = new Date(2020, 8, 5);
        //salaryDetails.setDate(date);

        Optional<Employee> employee = employeeRepository.findById(1);
        //salaryDetails.setEmployee(employee.get());
//        salaryDetails.setGrossSalary(45f);
//        salaryDetails.setNetSalary(78f);
//        salaryDetails.setBonus(87.5);
//        salaryDetails.setInsurance(47.1);
//        salaryDetails.setRaises(74.5);
        //       salaryDetails.setTaxes(58.9);

        addSalaryDetailsCommand salaryDetailsCommand = new addSalaryDetailsCommand(2, 0f, date, employee);

        String JSONSalaryDetails = objectMapper.writeValueAsString(salaryDetailsCommand);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/salaryController/addSalaryDetails")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123"))
                .contentType(MediaType.APPLICATION_JSON).content(JSONSalaryDetails))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BadArgumentsException))
                .andExpect(result -> Assertions.assertEquals("salary details with this id already exist!", result.getResolvedException().getMessage()));
    }

    @Test
    @Transactional
    public void addSalaryDetailsTesting3() throws Exception {
        SalaryDetails salaryDetails = new SalaryDetails();
        salaryDetails.setId(1);


        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        Date date = new Date(2020, 8, 5);
        //salaryDetails.setDate(date);

        Optional<Employee> employee = employeeRepository.findById(1);
        //salaryDetails.setEmployee(employee.get());
//        salaryDetails.setGrossSalary(45f);
//        salaryDetails.setNetSalary(78f);
//        salaryDetails.setBonus(87.5);
//        salaryDetails.setInsurance(47.1);
//        salaryDetails.setRaises(74.5);
        //       salaryDetails.setTaxes(58.9);

        addSalaryDetailsCommand salaryDetailsCommand = new addSalaryDetailsCommand(2, 1000f, null, employee);

        String JSONSalaryDetails = objectMapper.writeValueAsString(salaryDetailsCommand);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/salaryController/addSalaryDetails")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spongBob", "hr123"))
                .contentType(MediaType.APPLICATION_JSON).content(JSONSalaryDetails))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BadArgumentsException))
                .andExpect(result -> Assertions.assertEquals("date of salary details already exist!", result.getResolvedException().getMessage()));
    }

}