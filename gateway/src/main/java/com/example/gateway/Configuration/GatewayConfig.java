package com.example.gateway.Configuration;

import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

public class GatewayConfig {
    @Bean
    public RouterFunction<ServerResponse> customRoutes() {
        return GatewayRouterFunctions.route(RequestPredicates.path("/prueba"),
                HandlerFunctions.http("http://localhost:3600"));
    }
}
