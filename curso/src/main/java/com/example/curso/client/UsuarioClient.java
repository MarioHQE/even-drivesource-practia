package com.example.curso.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class UsuarioClient {
    @Autowired
    private WebClient.Builder webClientBuilder;
    private final String USUARIO_SERVICE_URL = "http://localhost:8081";

    public Boolean existuser(String idusuario) {
        return webClientBuilder.build()
                .get()
                .uri(USUARIO_SERVICE_URL + "/usuario/verificar/" + idusuario)
                .retrieve()
                .bodyToMono(Boolean.class)
                .doOnError(err -> System.err.println("Error al verificar usuario: " + err.getMessage()))
                .block();

    }

}
