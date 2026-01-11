package com.example.gateway.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.servlet.function.HandlerFilterFunction;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;
import java.util.stream.Collectors;

@Configuration
public class GatewayConfig {

        @Autowired
        private OAuth2AuthorizedClientService authorizedClientService;

        @Bean
        public RouterFunction<ServerResponse> customRoutes() {
                return GatewayRouterFunctions.

                                route(RequestPredicates.path("/prueba/**"),
                                                HandlerFunctions.http("http://localhost:3600"))
                                .andRoute(RequestPredicates.path("/curso/**"),
                                                HandlerFunctions.http("http://localhost:8082"))
                                .andRoute(RequestPredicates.path("/usuario/**"),
                                                HandlerFunctions.http("http://localhost:8083"));
        }

        /**
         * Agrega el token JWT en el header Authorization
         * y headers adicionales con info del usuario
         */
        private java.util.function.Function<org.springframework.web.servlet.function.ServerRequest, org.springframework.web.servlet.function.ServerRequest> addAuthorizationHeader() {
                return request -> {
                        var authentication = SecurityContextHolder.getContext().getAuthentication();

                        if (authentication instanceof OAuth2AuthenticationToken oauthToken) {
                                // Obtener el cliente OAuth2 autorizado
                                OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
                                                oauthToken.getAuthorizedClientRegistrationId(),
                                                oauthToken.getName());

                                if (client != null && client.getAccessToken() != null) {
                                        // Agregar el token JWT
                                        request.headers().header("Authorization")
                                                        .add("Bearer " + client.getAccessToken().getTokenValue());
                                }

                                // Agregar información del usuario en headers personalizados
                                var principal = oauthToken.getPrincipal();

                                if (principal.getAttribute("preferred_username") != null) {
                                        request.headers().header("X-User-Name")
                                                        .add(principal.getAttribute("preferred_username"));

                                }

                                if (principal.getAttribute("email") != null) {
                                        request.headers().header("X-User-Email").add(principal.getAttribute("email"));
                                }

                                // Roles
                                String roles = authentication.getAuthorities().stream()
                                                .map(auth -> auth.getAuthority())
                                                .collect(Collectors.joining(","));
                                request.headers().header("X-User-Roles").add(roles);

                                // Subject (ID de usuario en Keycloak)
                                if (principal instanceof org.springframework.security.oauth2.core.oidc.user.OidcUser oidcUser) {
                                        request.headers().header("X-User-Subject").add(oidcUser.getSubject());
                                }
                        }

                        return request;
                };
        }
}