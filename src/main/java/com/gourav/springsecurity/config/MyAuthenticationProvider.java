package com.gourav.springsecurity.config;

import com.gourav.springsecurity.model.Customer;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MyAuthenticationProvider implements AuthenticationProvider {
    public static Customer c = null;
    static {
         c = new Customer("gourav", "pass", "admin");
    }
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        var username = authentication.getName();
        var password = authentication.getCredentials().toString();

        if(c.getPassword().equals(password)){

            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(c.getRole()));
            return new UsernamePasswordAuthenticationToken(username, password, authorities);
        }
        throw new BadCredentialsException("Invalid Credentials!");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
