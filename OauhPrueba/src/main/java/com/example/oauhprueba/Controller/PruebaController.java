package com.example.oauhprueba.Controller;

import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/prueba")
@Slf4j
public class PruebaController {

    @GetMapping("/hello-1")
    public String helloadmin(
            @RequestHeader(value = "X-User-Name", required = false) String username,
            @RequestHeader(value = "X-User-Roles", required = false) String roles) {

        log.info("Usuario: {}", username);
        log.info("Roles: {}", roles);

        // El Gateway ya validó que tiene el rol admin-client-role
        return "Hello Spring boot with keycloak - ADMIN (User: " + username + ")";
    }

    @GetMapping("/hello-2")
    public String hellouser(
            @RequestHeader(value = "X-User-Name", required = false) String username,
            @RequestHeader(value = "X-User-Email", required = false) String email,
            @RequestHeader(value = "X-User-Roles", required = false) String roles) {

        log.info("Usuario: {}", username);
        log.info("Email: {}", email);
        log.info("Roles: {}", roles);

        // El Gateway ya validó que tiene rol user o admin
        return "Hello Spring boot with keycloak - USER (User: " + username + ", Email: " + email + ")";
    }
}