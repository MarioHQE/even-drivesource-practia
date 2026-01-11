package com.example.gateway.Configuration;

import com.example.gateway.GatewayApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.web.SecurityFilterChain;

import java.util.*;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final GatewayApplication gatewayApplication;

    SecurityConfig(GatewayApplication gatewayApplication) {
        this.gatewayApplication = gatewayApplication;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Rutas públicas
                        .requestMatchers("/home/**", "/public/**").permitAll()

                        // Rutas protegidas con roles específicos
                        // .requestMatchers("/prueba/hello-1").hasRole("admin-client-role")
                        // .requestMatchers("/prueba/hello-2").hasAnyRole("admin-client-role",
                        // "user-client-role")

                        // Otras rutas de prueba requieren autenticación
                        .requestMatchers("/prueba/**").permitAll()

                        // Curso y Usuario requieren autenticación
                        .requestMatchers("/curso/**", "/usuario/**").authenticated()

                        // Cualquier otra ruta requiere autenticación
                        .anyRequest().authenticated())
                .oauth2Login(Customizer.withDefaults())
                .oauth2Client(Customizer.withDefaults());

        return http.build();
    }

    /**
     * Extrae roles de Keycloak del token OIDC
     */
    @Bean
    public GrantedAuthoritiesMapper userAuthoritiesMapper() {
        return (authorities) -> {
            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();

            authorities.forEach(authority -> {
                if (authority instanceof OidcUserAuthority oidcUserAuthority) {
                    var userInfo = oidcUserAuthority.getUserInfo();

                    // Extraer roles de resource_access (client roles)
                    if (userInfo.hasClaim("resource_access")) {
                        Map<String, Object> resourceAccess = userInfo.getClaim("resource_access");

                        // Buscar roles del cliente específico
                        if (resourceAccess.containsKey("spring-client-api-rest")) {
                            Map<String, Object> clientRoles = (Map<String, Object>) resourceAccess
                                    .get("spring-client-api-rest");

                            if (clientRoles.containsKey("roles")) {
                                Collection<String> roles = (Collection<String>) clientRoles.get("roles");

                                mappedAuthorities.addAll(
                                        roles.stream()
                                                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                                                .collect(Collectors.toList()));
                            }
                        }
                    }

                    // Extraer realm_access roles (realm roles)
                    if (userInfo.hasClaim("realm_access")) {
                        Map<String, Object> realmAccess = userInfo.getClaim("realm_access");
                        if (realmAccess.containsKey("roles")) {
                            Collection<String> roles = (Collection<String>) realmAccess.get("roles");
                            mappedAuthorities.addAll(
                                    roles.stream()
                                            .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                                            .collect(Collectors.toList()));
                        }
                    }
                }
            });

            return mappedAuthorities;
        };
    }
}