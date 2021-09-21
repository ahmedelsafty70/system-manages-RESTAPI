package com.example.Phase12;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
@SpringBootApplication
public class QuartzTest {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	public static void main(String[] args){
		SpringApplication.run(QuartzTest.class, args);
	}
	@Configuration
	@ConditionalOnProperty(value = "scheduling.enabled", havingValue = "true", matchIfMissing = true)
	@EnableScheduling
	public class SchedulingConfiguration {}
	}

