package com.ticket_platform.ticket_platform.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
    
    @Bean
    @SuppressWarnings("removal")
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers("/tickets/create", "/tickets/edit/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/tickets/create").hasAuthority("ADMIN")
                .requestMatchers("/tickets/search/**").hasAuthority("ADMIN")
                .requestMatchers("/tickets", "/tickets/**").hasAnyAuthority("OPERATORE", "ADMIN")
                .requestMatchers("/**").hasAnyAuthority("OPERATORE", "ADMIN")
                .requestMatchers("/api/tickets", "/api/tickets/categoria", "/api/tickets/stato").permitAll()
                .and().formLogin()
                .and().logout()
                .and().exceptionHandling();
        return http.build();
    }

    @Bean
    DatabaseUserDetailsService userDetailsService() {
        return new DatabaseUserDetailsService();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

}
