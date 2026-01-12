package com.example.oauhprueba.Controller;

import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/prueba")
@Slf4j
public class PruebaController {

    @GetMapping("/hello-1")
    // @PreAuthorize("hasRole('admin-client_role')")
    public String helloadmin(@AuthenticationPrincipal String princString) {
        log.info("Principal {}", princString);
        return "Hello Spring boot with keycloak- ADMIN";
    }

    @GetMapping("/hello-2")
    public String hellouser() {
        log.info("Principal {}", SecurityContextHolder.getContext().getAuthentication().getName());

        SecurityContextHolder.getContext().getAuthentication().getAuthorities().forEach(auth -> {
            log.info("Authority: {}", auth.getAuthority());
            log.info("Principal from SecurityContext: {}",
                    SecurityContextHolder.getContext().getAuthentication().getName());
        });
        return "Hello Spring boot with keycloak -USER";
    }
}
