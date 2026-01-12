package com.example.gateway.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import com.example.gateway.JwtConverter.jwtConverter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private jwtConverter jwtConverter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/home/**").permitAll()
                        .requestMatchers("/prueba/hello-1").hasRole("admin-client-role")
                        .requestMatchers("/prueba/hello-2").hasAnyRole("user-client-role", "admin-client-role")
                        .anyRequest().authenticated())
                // OAuth2 Login maneja la autenticación con Keycloak
                .oauth2Login(Customizer.withDefaults())
                // OAuth2 Client permite usar el token para llamadas al backend
                .oauth2Client(Customizer.withDefaults())
                .oauth2ResourceServer(
                        resourceserver -> resourceserver.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtConverter)))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}