package com.juliomesquita.core.services.keycloak.implementations;

import com.juliomesquita.core.services.keycloak.dtos.loginflow.TokenClientKeycloak;
import com.juliomesquita.core.services.keycloak.interfaces.LoginClientKeycloakService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.Objects;

@Component
public class LoginClientKeycloakServiceImpl implements LoginClientKeycloakService {
   @Value("${spring.security.oauth2.client.provider.keycloak.token-uri}")
   private String keycloakTokenClientUrl;

   @Value("${configs.keycloak.client-id}")
   private String clientId;

   @Value("${configs.keycloak.client-secret}")
   private String clientSecret;

   private final RestClient restClient;

   public LoginClientKeycloakServiceImpl(final RestClient restClient) {
      this.restClient = Objects.requireNonNull(restClient);
   }

   @Override
   public TokenClientKeycloak getTokenClient() {
      MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
      body.add("client_id", clientId);
      body.add("client_secret", clientSecret);
      body.add("grant_type", "client_credentials");

      return this.restClient
              .post()
              .uri(keycloakTokenClientUrl)
              .headers(headers -> headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED))
              .body(body)
              .retrieve()
              .toEntity(TokenClientKeycloak.class)
              .getBody();
   }
}
