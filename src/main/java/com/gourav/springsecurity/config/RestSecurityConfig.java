package com.gourav.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;
//import static sun.net.ftp.FtpDirEntry.Permission.USER;

@Configuration
public class RestSecurityConfig {

    @Bean
    SecurityFilterChain myCustomSecurityChainFilter(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(request-> {
                     request.requestMatchers("/contact", "/notice", "/h2-console/**").permitAll();
                     request.requestMatchers("/myAccount", "/myCards", "/myProfile").authenticated();
                 }

         );
         http.formLogin(withDefaults());
         http.httpBasic(withDefaults());

         return http.build();
    }

//    @Bean
//    public UserDetailsService customService(DataSource dataSource) {
//        return new JdbcUserDetailsManager(dataSource);
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

}
