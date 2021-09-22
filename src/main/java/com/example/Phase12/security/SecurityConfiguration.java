package com.example.Phase12.security;

import com.example.Phase12.exceptions.ResourceNotFoundException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {



    private UserPrincipalDetailsService userPrincipalDetailsService;
    private Http401AuthenticationEntryPoint entryPoint;

    public SecurityConfiguration(UserPrincipalDetailsService userPrincipalDetailsService, Http401AuthenticationEntryPoint authenticationEntryPoint){
        this.userPrincipalDetailsService = userPrincipalDetailsService;
        this.entryPoint = authenticationEntryPoint;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth){
        auth.authenticationProvider(authenticationProvider());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();
        http
                .authorizeRequests()

//                .antMatchers("HumanResources/employees/gettingEmployee/**").hasAnyRole("HR","MANAGER")
                .antMatchers("/HumanResources/employees/get/**").hasAnyRole("HR","MANAGER")
                .antMatchers("/HumanResources/employees/add").hasRole("HR")
                .antMatchers("/departmentController/**").hasRole("HR")
                .antMatchers("/departmentController/GetDep/**").hasAnyRole("HR","MANAGER")
                .antMatchers("/teamController/adding").hasRole("HR")
                .antMatchers("/teamController/gettingEmployeesUnderTeam/**").hasAnyRole("HR","MANAGER")
                .antMatchers("/teamController/getTeam/**").hasRole("HR")
                .antMatchers("/HumanResources/employees/gettingUnderEmployees/**").hasAnyRole("HR","MANAGER")
                .antMatchers("/VacationController/addVacation").hasRole("HR")
                .antMatchers("/VacationController/get/**").hasAnyRole("HR","MANAGER")
                .antMatchers("/salaryController/getSalaryDetails/**").hasAnyRole("HR","MANAGER")
                .antMatchers("/salaryController/addSalaryDetails").hasRole("HR")
                .antMatchers("/HumanResources/notForHR/**").hasRole("USER")
                .antMatchers("/HumanResources/Earning/get/**").hasAnyRole("HR","MANAGER")
                .antMatchers("/HumanResources/Earning/add/**").hasRole("HR")
                .and()
                .httpBasic().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint(entryPoint);
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.userPrincipalDetailsService);

        return daoAuthenticationProvider;
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }



}
