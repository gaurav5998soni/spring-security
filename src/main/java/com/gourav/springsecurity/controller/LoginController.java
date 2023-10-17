package com.gourav.springsecurity.controller;

import com.gourav.springsecurity.model.Customer;
import com.gourav.springsecurity.repository.CustomerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;

@RestController
public class LoginController {

    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;

    public LoginController(PasswordEncoder passwordEncoder, CustomerRepository customerRepository) {
        this.passwordEncoder = passwordEncoder;
        this.customerRepository = customerRepository;
    }

    @PostMapping("/register")
    public String register(@RequestBody Customer customer) {

        try{
            customer.setPwd(passwordEncoder.encode(customer.getPwd()));
            customer.setCreateDt(String.valueOf(new Date(System.currentTimeMillis())));

            var customerId = customerRepository.save(customer);

            if(customerId!=null && customer.getId()>0) {
                return "New Customer Registered Successfully!";
            }

        } catch(Exception e) {
            return "Something wents wrong, please try again after some time";
        }
        return "Something wents wrong, please try again after some time";
    }
}
