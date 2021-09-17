package com.example.Phase12;

import com.example.Phase12.repository.EmployeeRepository;
import com.example.Phase12.repository.VacationRepository;
import com.example.Phase12.sections.ConstantsDeduction;
import com.example.Phase12.sections.Employee;
import com.example.Phase12.sections.SalaryDetails;
import com.example.Phase12.service.EmployeeService;
import com.example.Phase12.service.SalaryDetailsService;
import org.modelmapper.ModelMapper;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

@SpringBootApplication
public class QuartzTest {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}


	public static void main(String[] args) throws SchedulerException {
		SpringApplication.run(QuartzTest.class, args);


		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

		scheduler.start();

		JobDetail job = newJob(salaryUpdateJob.class)
				.withIdentity("bank-transfer")
				.build();

		SimpleTrigger trigger = newTrigger().withIdentity("trigger")
				.startNow()
				.withSchedule(simpleSchedule().withIntervalInSeconds(5).repeatForever())
				.build();

		scheduler.scheduleJob(job, trigger);

	}

	public static class salaryUpdateJob implements Job {
//
//		private EmployeeRepository employeeRepository;
//		private VacationRepository vacationRepository;
//
//		public salaryUpdateJob(EmployeeRepository employeeRepository, VacationRepository vacationRepository) {
//			this.employeeRepository = employeeRepository;
//			this.vacationRepository = vacationRepository;
//		}

		@Override
		public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

//				Employee employee = Employee(1,"ahmed", )
//
//				for (Employee employee : Employees)
//				{
//					SalaryDetails salaryDetails = new SalaryDetails();
//
//					Date date = Date.valueOf(LocalDate.now());
//
//					salaryDetails.setDate(date);
//
//					String formattedDate = String.format("yyyy/MM", date);
//
//					String[] arrayOfYearsAndMonths = formattedDate.split("/");
//
//
//					int yearInInteger = Integer.parseInt(arrayOfYearsAndMonths[0]);
//
//					int noOfDaysExceeded = vacationRepository.counterForTheExceededDays(employee.getIdEmployee(),yearInInteger);
//
//					double calculatingNetSalary = employee.getGrossSalary() + employee.getBonus() + employee.getRaises()
//							- ConstantsDeduction.insurance - (noOfDaysExceeded * (employee.getGrossSalary()/22));
//
//					employee.setBonus(0D);
//					employee.setRaises(0D);
//
//
//					salaryDetails.setActualSalary((float)(calculatingNetSalary - (calculatingNetSalary * ConstantsDeduction.taxes)));
//
//					salaryDetails.setEmployee(employee);
//
//					System.out.println("hiiiiiiiiiiiiiiiiiiii");
//				}
//			}
//		}
		}
	}
}
