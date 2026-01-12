package com.example.gateway.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import com.example.gateway.JwtConverter.jwtConverter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
        @Autowired
        private jwtConverter jwtconverter;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                return http
                                .csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests(auth -> auth
                                                .anyRequest().authenticated())
                                .oauth2Login(Customizer.withDefaults())
                                .oauth2ResourceServer(
                                                resourceserver -> resourceserver.jwt(
                                                                jwt -> jwt.jwtAuthenticationConverter(
                                                                                jwtconverter)))

                                .build();

        }
}