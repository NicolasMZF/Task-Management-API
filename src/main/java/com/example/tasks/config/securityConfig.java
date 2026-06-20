package com.example.tasks.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * csrf disabled for local tests
 * Allows any request
 */

@Configuration
@EnableWebSecurity
public class securityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        System.out.println("Security LOADED");
        http
                //Deactivated for local tests
            .csrf(csrf -> csrf.disable())
                //Allow the access
            .authorizeHttpRequests(auth -> auth
                    .anyRequest().permitAll()
            );
        return http.build();
    }
}
