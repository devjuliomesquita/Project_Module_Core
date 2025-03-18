package com.juliomesquita.core.services.keycloak.implementations;

import com.juliomesquita.core.services.keycloak.dtos.KeycloakTest;
import com.juliomesquita.core.services.keycloak.dtos.loginflow.CreateUserKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.loginflow.CredentialsUserKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.loginflow.TokenClientKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.loginflow.TokenUserKeycloak;
import com.juliomesquita.core.services.keycloak.interfaces.LoginFlowKeycloakService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.Objects;

@Component
public class LoginFlowKeycloakServiceImpl implements LoginFlowKeycloakService {
   @Value("${spring.security.oauth2.client.provider.keycloak.token-uri}")
   private String keycloakUrl;

   @Value("${configs.keycloak.realm}")
   private String realm;

   @Value("${configs.keycloak.client-id}")
   private String clientId;

   @Value("${configs.keycloak.client-secret}")
   private String clientSecret;

   private final RestClient restClient;

   public LoginFlowKeycloakServiceImpl(final RestClient restClient) {
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
              .uri(keycloakUrl)
              .headers(headers -> headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED))
              .body(body)
              .retrieve()
              .toEntity(TokenClientKeycloak.class)
              .getBody();
   }

   @Override
   public void createUser(CreateUserKeycloak data) {

   }

   @Override
   public TokenUserKeycloak loginUser(String username, String password) {
      MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
      body.add("client_id", clientId);
      body.add("client_secret", clientSecret);
      body.add("grant_type", "password");
      body.add("username", username);
      body.add("password", password);

      return this.restClient
              .post()
              .uri(keycloakUrl)
              .headers(headers -> headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED))
              .body(body)
              .retrieve()
              .toEntity(TokenUserKeycloak.class)
              .getBody();
   }

   @Override
   public TokenUserKeycloak refreshTokenUser(String refreshToken) {
      return null;
   }

   @Override
   public void logoutUser(String refreshToken) {

   }

   @Override
   public void resetPassword(String userId, CredentialsUserKeycloak credentials) {

   }
}

