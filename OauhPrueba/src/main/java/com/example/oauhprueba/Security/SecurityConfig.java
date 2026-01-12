package com.example.oauhprueba.Security;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final String jwkSetUri = "http://localhost:9090/realms/springboot-realm-dev/protocol/openid-connect/certs";

    @Autowired
    private jwtAuthenticationConverter jwtAuthConverter;

    @Bean
    public SecurityFilterChain filterchain(HttpSecurity http) throws Exception {
        return http.csrf(csrfconfigure -> csrfconfigure.disable())
                .authorizeHttpRequests(authorizereq -> authorizereq.anyRequest().authenticated())
                .oauth2ResourceServer(resourceserver -> resourceserver.jwt(jwt -> {
                    jwt.jwtAuthenticationConverter(jwtAuthConverter);

                }))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();

    }

    // @Bean
    // public JwtAuthenticationConverter nsa2AuthenticationConverter() {
    // var converter = new JwtAuthenticationConverter();
    // converter.setJwtGrantedAuthoritiesConverter(new
    // CustomJwtGrantedAuthoritiesConverter());
    // return converter;
    // }
}
