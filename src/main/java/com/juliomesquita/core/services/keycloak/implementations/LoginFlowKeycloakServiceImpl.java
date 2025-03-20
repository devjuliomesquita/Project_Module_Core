package com.juliomesquita.core.services.keycloak.implementations;

import com.juliomesquita.core.services.keycloak.dtos.loginflow.CreateUserKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.loginflow.CredentialsUserKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.loginflow.TokenClientKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.loginflow.TokenUserKeycloak;
import com.juliomesquita.core.services.keycloak.interfaces.LoginClientKeycloakService;
import com.juliomesquita.core.services.keycloak.interfaces.LoginFlowKeycloakService;
import com.juliomesquita.core.shared.utils.UtilsMethods;
import com.juliomesquita.core.shared.validations.Notification;
import io.vavr.API;
import io.vavr.control.Either;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.net.URI;
import java.util.Objects;
import java.util.function.Function;

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
   public Either<Notification, String> createUser(@NonNull CreateUserKeycloak data) {
      final TokenClientKeycloak tokenClient = this.loginClientKeycloakService.getTokenClient();
      final String uri = "/users";
      final String token = "Bearer %s".formatted(tokenClient.access_token());

      return API.Try(() -> {
                         final URI location = this.restClient
                                 .post()
                                 .uri(keycloakUrlBase + uri)
                                 .headers(headers -> {
                                    headers.add(HttpHeaders.AUTHORIZATION, token);
                                    headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
                                 })
                                 .body(data)
                                 .retrieve()
                                 .toEntity(Void.class)
                                 .getHeaders()
                                 .getLocation();
                         return Objects.requireNonNull(location).toString();
                      }
              ).toEither()
              .bimap(Notification::create, UtilsMethods.extractId);

   }

   @Override
   public Either<Notification, TokenUserKeycloak> loginUser(@NonNull String username, @NonNull String password) {
      MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
      body.add("client_id", clientId);
      body.add("client_secret", clientSecret);
      body.add("grant_type", "password");
      body.add("username", username);
      body.add("password", password);

      return API.Try(() -> this.restClient
                      .post()
                      .uri(keycloakTokenClientUrl)
                      .headers(headers -> headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED))
                      .body(body)
                      .retrieve()
                      .toEntity(TokenUserKeycloak.class)
                      .getBody()
              )
              .toEither()
              .bimap(Notification::create, Function.identity());
   }

   @Override
   public Either<Notification, TokenUserKeycloak> refreshTokenUser(@NonNull String refreshToken) {
      MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
      body.add("client_id", clientId);
      body.add("client_secret", clientSecret);
      body.add("grant_type", "refresh_token");
      body.add("refresh_token", refreshToken);

      return API.Try(() -> this.restClient
                      .post()
                      .uri(keycloakTokenClientUrl)
                      .headers(headers -> headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED))
                      .body(body)
                      .retrieve()
                      .toEntity(TokenUserKeycloak.class)
                      .getBody()
              )
              .toEither()
              .bimap(Notification::create, Function.identity());
   }

   @Override
   public Either<Notification, Void> logoutUser(@NonNull String refreshToken) {
      MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
      body.add("client_id", clientId);
      body.add("client_secret", clientSecret);
      body.add("refresh_token", refreshToken);
      final String uri = "/protocol/openid-connect/logout";
      return API.Try(() -> this.restClient
                      .post()
                      .uri(keycloakUrlBase + uri)
                      .headers(headers -> headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED))
                      .body(body)
                      .retrieve()
                      .toEntity(Void.class)
                      .getBody()
              )
              .toEither()
              .bimap(Notification::create, Function.identity());
   }

   @Override
   public Either<Notification, Void> resetPassword(@NonNull String userId, @NonNull CredentialsUserKeycloak credentials) {
      final TokenClientKeycloak tokenClient = this.loginClientKeycloakService.getTokenClient();
      final String uri = "/users/%s/reset-password".formatted(userId);
      final String token = "Bearer %s".formatted(tokenClient.access_token());

      return API.Try(() -> this.restClient
                      .put()
                      .uri(keycloakUrlBase + uri)
                      .headers(headers -> headers.add(HttpHeaders.AUTHORIZATION, token))
                      .body(credentials)
                      .retrieve()
                      .toEntity(Void.class)
                      .getBody()
              )
              .toEither()
              .bimap(Notification::create, Function.identity());
   }
}

