package com.example.usuario.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SegurityConfig {
    @Bean
    public SecurityFilterChain filterchain(HttpSecurity http) throws Exception {
        return http.csrf(csrfconfigure -> csrfconfigure.disable())
                .cors(corsconfigure -> corsconfigure.disable())
                .authorizeHttpRequests(authorizereq -> authorizereq
                        .requestMatchers("/usuario/**").permitAll()
                        .requestMatchers("http://localhost:8082/**").permitAll()
                        .anyRequest().authenticated())
                .oauth2ResourceServer(
                        oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(new JwtAuthenticationConverter())))
                .build();
    }
}
