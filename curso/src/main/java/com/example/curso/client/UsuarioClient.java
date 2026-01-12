package com.example.curso.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class UsuarioClient {

    private final RestTemplate restTemplate;
    private static final String USUARIO_SERVICE_URL = "http://localhost:8080";

    @Autowired
    public UsuarioClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Boolean existuser(String idusuario) {
        try {
            String url = USUARIO_SERVICE_URL + "/usuario/verificar/" + idusuario;
            return restTemplate.getForObject(url, Boolean.class);
        } catch (RestClientException e) {
            System.err.println("Error al verificar usuario: " + e.getMessage());
            return false;
        }
    }
}