package com.example.curso.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Slf4j
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(bearerTokenInterceptor());
        restTemplate.setInterceptors(interceptors);

        return restTemplate;
    }

    /**
     * Interceptor que añade automáticamente el token Bearer a las peticiones
     */
    private ClientHttpRequestInterceptor bearerTokenInterceptor() {
        return (request, body, execution) -> {
            var authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication instanceof JwtAuthenticationToken jwtAuth) {
                String token = jwtAuth.getToken().getTokenValue();
                log.info("Adding Bearer token to request: {}", token);
                request.getHeaders().setBearerAuth(token);
            }

            return execution.execute(request, body);
        };
    }
}