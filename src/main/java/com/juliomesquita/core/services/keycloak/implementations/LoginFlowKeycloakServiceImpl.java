package com.juliomesquita.core.services.keycloak.implementations;

import com.juliomesquita.core.services.keycloak.dtos.loginflow.CreateUserKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.loginflow.CredentialsUserKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.loginflow.TokenClientKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.loginflow.TokenUserKeycloak;
import com.juliomesquita.core.services.keycloak.interfaces.LoginClientKeycloakService;
import com.juliomesquita.core.services.keycloak.interfaces.LoginFlowKeycloakService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.Objects;

@Component
public class LoginFlowKeycloakServiceImpl implements LoginFlowKeycloakService {
   @Value("${spring.security.oauth2.client.provider.keycloak.token-uri}")
   private String keycloakTokenClientUrl;

   @Value("${configs.keycloak.auth-server-url-admin}")
   private String keycloakUrlBase;

   @Value("${configs.keycloak.client-id}")
   private String clientId;

   @Value("${configs.keycloak.client-secret}")
   private String clientSecret;

   private final RestClient restClient;
   private final LoginClientKeycloakService loginClientKeycloakService;

   public LoginFlowKeycloakServiceImpl(
           final RestClient restClient,
           final LoginClientKeycloakService loginClientKeycloakService
   ) {
      this.restClient = Objects.requireNonNull(restClient);
      this.loginClientKeycloakService = Objects.requireNonNull(loginClientKeycloakService);
   }

   @Override
   public void createUser(@NonNull CreateUserKeycloak data) {
      final TokenClientKeycloak tokenClient = this.loginClientKeycloakService.getTokenClient();
      final String uri = "/users";
      final String token = "Bearer %s".formatted(tokenClient.access_token());

      this.restClient
              .post()
              .uri(keycloakUrlBase + uri)
              .headers(headers -> {
                 headers.add(HttpHeaders.AUTHORIZATION, token);
                 headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
              })
              .body(data)
              .retrieve()
              .toBodilessEntity();
   }

   @Override
   public TokenUserKeycloak loginUser(@NonNull String username, @NonNull String password) {
      MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
      body.add("client_id", clientId);
      body.add("client_secret", clientSecret);
      body.add("grant_type", "password");
      body.add("username", username);
      body.add("password", password);

      return this.restClient
              .post()
              .uri(keycloakTokenClientUrl)
              .headers(headers -> headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED))
              .body(body)
              .retrieve()
              .toEntity(TokenUserKeycloak.class)
              .getBody();
   }

   @Override
   public TokenUserKeycloak refreshTokenUser(@NonNull String refreshToken) {
      MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
      body.add("client_id", clientId);
      body.add("client_secret", clientSecret);
      body.add("grant_type", "refresh_token");
      body.add("refresh_token", refreshToken);
      return this.restClient
              .post()
              .uri(keycloakTokenClientUrl)
              .headers(headers -> headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED))
              .body(body)
              .retrieve()
              .toEntity(TokenUserKeycloak.class)
              .getBody();
   }

   @Override
   public void logoutUser(@NonNull String refreshToken) {
      MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
      body.add("client_id", clientId);
      body.add("client_secret", clientSecret);
      body.add("refresh_token", refreshToken);
      final String uri = "/protocol/openid-connect/logout";
      this.restClient
              .post()
              .uri(keycloakUrlBase + uri)
              .headers(headers -> headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED))
              .body(body);
   }

   @Override
   public void resetPassword(@NonNull String userId, @NonNull CredentialsUserKeycloak credentials) {
      final TokenClientKeycloak tokenClient = this.loginClientKeycloakService.getTokenClient();
      final String uri = "/users/%s/reset-password".formatted(userId);
      final String token = "Bearer %s".formatted(tokenClient.access_token());

      this.restClient
              .put()
              .uri(keycloakUrlBase + uri)
              .headers(headers -> headers.add(HttpHeaders.AUTHORIZATION, token))
              .body(credentials)
              .retrieve()
              .toBodilessEntity();
   }
}

