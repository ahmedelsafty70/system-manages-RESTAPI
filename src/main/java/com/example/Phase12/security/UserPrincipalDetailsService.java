package com.example.Phase12.security;

import com.example.Phase12.repository.EmployeeRepository;
import com.example.Phase12.sections.Employee;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserPrincipalDetailsService implements UserDetailsService {

    private EmployeeRepository employeeRepository;

    public UserPrincipalDetailsService(EmployeeRepository employeeRepository){
            this.employeeRepository = employeeRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Employee employee = this.employeeRepository.findByUsername(username);
        UserPrincipal userPrincipal = new UserPrincipal(employee);
        return userPrincipal;
    }

}
