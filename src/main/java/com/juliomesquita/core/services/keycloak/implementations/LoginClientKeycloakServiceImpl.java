package com.juliomesquita.core.services.keycloak.implementations;

import com.juliomesquita.core.services.keycloak.dtos.loginflow.TokenClientKeycloak;
import com.juliomesquita.core.services.keycloak.interfaces.LoginClientKeycloakService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.time.Instant;
import java.util.Objects;

@Component
public class LoginClientKeycloakServiceImpl implements LoginClientKeycloakService {
   @Value("${spring.security.oauth2.client.provider.keycloak.token-uri}")
   private String keycloakTokenClientUrl;

   @Value("${configs.keycloak.client-id}")
   private String clientId;

   @Value("${configs.keycloak.client-secret}")
   private String clientSecret;

   private TokenClientKeycloak tokenClientKeycloakCache;
   private Instant instanteTokenInspiration;

   private final RestClient restClient;

   public LoginClientKeycloakServiceImpl(final RestClient restClient) {
      this.restClient = Objects.requireNonNull(restClient);
   }

   @Override
   public synchronized TokenClientKeycloak getTokenClient() {
      if (tokenClientKeycloakCache != null && Instant.now().isBefore(instanteTokenInspiration)) {
         System.out.println(tokenClientKeycloakCache.access_token());
         return this.tokenClientKeycloakCache;
      }

      MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
      body.add("client_id", clientId);
      body.add("client_secret", clientSecret);
      body.add("grant_type", "client_credentials");

      final TokenClientKeycloak tokenClientKeycloak = this.restClient
              .post()
              .uri(keycloakTokenClientUrl)
              .headers(headers -> headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED))
              .body(body)
              .retrieve()
              .toEntity(TokenClientKeycloak.class)
              .getBody();

      this.tokenClientKeycloakCache = Objects.requireNonNull(tokenClientKeycloak);
      this.instanteTokenInspiration = Instant.now().plusSeconds(
              Objects.requireNonNull(tokenClientKeycloak).expires_in() - 10);
      System.out.println(tokenClientKeycloak.access_token());
      return tokenClientKeycloak;
   }
}
