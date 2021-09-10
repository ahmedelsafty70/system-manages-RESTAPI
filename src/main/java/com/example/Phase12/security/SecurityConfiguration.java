package com.example.Phase12.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {



    private UserPrincipalDetailsService userPrincipalDetailsService;

    public SecurityConfiguration(UserPrincipalDetailsService userPrincipalDetailsService){
        this.userPrincipalDetailsService = userPrincipalDetailsService;
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
                .antMatchers("/HumanResources/employees/gettingEmployee/{id}").authenticated()

                .antMatchers("/HumanResources/employees/**").hasRole("HR")
                .antMatchers("/departmentController/add").hasRole("HR")
                .antMatchers("/departmentController/GetDep/**").hasAnyRole("HR","MANAGER")
                .antMatchers("/teamController/adding").hasRole("HR")
                .antMatchers("/teamController/gettingEmployeesUnderTeam/**").hasAnyRole("HR","MANAGER")
                .antMatchers("/HumanResources/employees/gettingUnderEmployees/**").hasAnyRole("HR","MANAGER")
                .antMatchers("/VacationController/addVacation").hasRole("HR")
                .antMatchers("/VacationController/get/**").hasAnyRole("HR","MANAGER")
                .antMatchers("/salaryController/getSalaryDetails/**").hasAnyRole("HR","MANAGER")
                .antMatchers("/salaryController/addSalaryDetails").hasRole("HR")
                .antMatchers("/HumanResources/employees/deleting/**").hasRole("HR")



                .antMatchers("/HumanResources/employees/gettingUnderEmployees/{id}").hasAuthority("gettingEmployees")
                //.antMatchers("/HumanResources/employees/gettingEmployee/**").hasRole("")

                .antMatchers("/HumanResources/employees/deleting/{id}").hasRole("ADMIN")
              //  .antMatchers("/HumanResources/employees/gettingEmployeesRecursively/{id}").hasRole("MANAGER")
                .antMatchers("/HumanResources/employees/users").hasRole("MANAGER")
                .and()
                .httpBasic();
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
