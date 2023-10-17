package com.gourav.springsecurity.config;

import com.gourav.springsecurity.model.Authority;
import com.gourav.springsecurity.repository.CustomerRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class MyAuthenticationProvider implements AuthenticationProvider {

    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;

    public MyAuthenticationProvider(PasswordEncoder passwordEncoder, CustomerRepository customerRepository) {
        this.passwordEncoder = passwordEncoder;
        this.customerRepository = customerRepository;
    }
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        var username = authentication.getName();
        var password = authentication.getCredentials().toString();

        var customer = customerRepository.findByEmail(username);
        if(customer.size()>0) {
            var pass = customer.get(0).getPwd();
            if (passwordEncoder.matches(password, pass)) {
                return new UsernamePasswordAuthenticationToken(username, password, getAuthorities(customer.get(0).getAuthorities()));
            } else {
                throw new BadCredentialsException("Invalid Credentials!");
            }
        } else {

            throw new BadCredentialsException("No User Found!");
        }
    }

    private List<GrantedAuthority> getAuthorities(Set<Authority> authorities) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for(Authority authority: authorities)
            grantedAuthorities.add(new SimpleGrantedAuthority(authority.getName()));

        return grantedAuthorities;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
