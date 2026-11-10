package com.example.tasks.config;

import com.example.tasks.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * csrf disabled (use of JWT no cookies)
 */


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class securityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // 1. Disable CSRF (use of JWT no cookies)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**", "/error").permitAll() // 2. Allow access without token only to login/register
                        .anyRequest().authenticated() // 3. Everything else needs the token
                )
                .sessionManagement(session->session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 4. No sessions, pure JWT
                )
                .authenticationProvider(authenticationProvider) // 5. Tell Spring how to authenticate
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); // 6. Add the filter

        return http.build();
    }
}
