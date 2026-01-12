package com.example.curso.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import com.example.curso.JwtConverter.jwtConverter;

@Configuration
@EnableWebSecurity
public class SegurityConfig {
        @Autowired
        jwtConverter jwtconverter;

        @Bean
        public SecurityFilterChain filterchain(HttpSecurity http) throws Exception {
                return http.csrf(csrfconfigure -> csrfconfigure.disable())
                                .cors(corsconfigure -> corsconfigure.disable())
                                .authorizeHttpRequests(authorizereq -> authorizereq
                                                .requestMatchers("/curso/**").permitAll()
                                                .requestMatchers("http://localhost:8082/**").permitAll()
                                                .anyRequest().authenticated())

                                .oauth2ResourceServer(
                                                oauth2 -> oauth2.jwt(
                                                                jwt -> jwt.jwtAuthenticationConverter(jwtconverter)))
                                .build();
        }
}
