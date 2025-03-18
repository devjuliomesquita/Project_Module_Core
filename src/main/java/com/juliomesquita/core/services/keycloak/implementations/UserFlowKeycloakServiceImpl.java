package com.juliomesquita.core.services.keycloak.implementations;

import com.juliomesquita.core.services.keycloak.dtos.loginflow.TokenClientKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.userflow.ListUserInformationKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.userflow.UserDataKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.userflow.UserInformationKeycloak;
import com.juliomesquita.core.services.keycloak.interfaces.LoginClientKeycloakService;
import com.juliomesquita.core.services.keycloak.interfaces.UserFlowKeycloakService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class UserFlowKeycloakServiceImpl implements UserFlowKeycloakService {
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

   public UserFlowKeycloakServiceImpl(
           final RestClient restClient,
           final LoginClientKeycloakService loginClientKeycloakService
   ) {
      this.restClient = Objects.requireNonNull(restClient);
      this.loginClientKeycloakService = Objects.requireNonNull(loginClientKeycloakService);
   }

   @Override
   public void updateUser(@NonNull String userId, @NonNull UserDataKeycloak data) {
      final TokenClientKeycloak tokenClient = this.loginClientKeycloakService.getTokenClient();
      final String uri = "/users/%s".formatted(userId);
      final String token = "Bearer %s".formatted(tokenClient.access_token());

      this.restClient
              .put()
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
   public void deleteUser(@NonNull String userId) {
      final TokenClientKeycloak tokenClient = this.loginClientKeycloakService.getTokenClient();
      final String uri = "/users/%s".formatted(userId);
      final String token = "Bearer %s".formatted(tokenClient.access_token());

      this.restClient
              .delete()
              .uri(keycloakUrlBase + uri)
              .headers(headers -> {
                 headers.add(HttpHeaders.AUTHORIZATION, token);
                 headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
              })
              .retrieve()
              .toBodilessEntity();
   }

   @Override
   public void activateOrDeactivateUser(@NonNull String userId, @NonNull Boolean enabled) {
      final TokenClientKeycloak tokenClient = this.loginClientKeycloakService.getTokenClient();
      final String uri = "/users/%s".formatted(userId);
      final String token = "Bearer %s".formatted(tokenClient.access_token());
      final Map<String, Object> body = new HashMap<>();
      body.put("enabled", enabled);

      this.restClient
              .put()
              .uri(keycloakUrlBase + uri)
              .headers(headers -> {
                 headers.add(HttpHeaders.AUTHORIZATION, token);
                 headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
              })
              .body(body)
              .retrieve()
              .toBodilessEntity();
   }

   @Override
   public List<UserInformationKeycloak> findUsers() {
      final TokenClientKeycloak tokenClient = this.loginClientKeycloakService.getTokenClient();
      final String uri = "/users";
      final String token = "Bearer %s".formatted(tokenClient.access_token());

      return this.restClient
              .get()
              .uri(keycloakUrlBase + uri)
              .headers(headers -> {
                 headers.add(HttpHeaders.AUTHORIZATION, token);
                 headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
              })
              .retrieve()
              .body(new ParameterizedTypeReference<List<UserInformationKeycloak>>() {
              });

   }

   @Override
   public List<UserInformationKeycloak> findUsersByFilter(@NonNull String email, @NonNull Boolean enabled) {
      final TokenClientKeycloak tokenClient = this.loginClientKeycloakService.getTokenClient();
      final String uri = "/users";
      final String token = "Bearer %s".formatted(tokenClient.access_token());
      final String url = String.format("%s%s?email=%s&enabled=%b", keycloakUrlBase, uri, email, enabled);

      return this.restClient
              .get()
              .uri(url)
              .headers(headers -> {
                 headers.add(HttpHeaders.AUTHORIZATION, token);
                 headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
              })
              .retrieve()
              .body(new ParameterizedTypeReference<List<UserInformationKeycloak>>() {
              });
   }
}
