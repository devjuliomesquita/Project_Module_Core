package com.juliomesquita.core.services.keycloak.implementations;

import com.juliomesquita.core.services.keycloak.dtos.loginflow.TokenClientKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.userflow.ListUserInformationKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.userflow.UserDataKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.userflow.UserInformationKeycloak;
import com.juliomesquita.core.services.keycloak.interfaces.LoginClientKeycloakService;
import com.juliomesquita.core.services.keycloak.interfaces.UserFlowKeycloakService;
import com.juliomesquita.core.shared.pagination.Pagination;
import com.juliomesquita.core.shared.pagination.SearchQuery;
import com.juliomesquita.core.shared.utils.UtilsMethods;
import com.juliomesquita.core.shared.validations.Notification;
import io.vavr.API;
import io.vavr.control.Either;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.*;
import java.util.function.Function;

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
   public Either<Notification, Void> updateUser(@NonNull String userId, @NonNull UserDataKeycloak data) {
      final TokenClientKeycloak tokenClient = this.loginClientKeycloakService.getTokenClient();
      final String uri = "/users/%s".formatted(userId);
      final String token = "Bearer %s".formatted(tokenClient.access_token());

      return API.Try(() -> this.restClient
                      .put()
                      .uri(keycloakUrlBase + uri)
                      .headers(headers -> {
                         headers.add(HttpHeaders.AUTHORIZATION, token);
                         headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
                      })
                      .body(data)
                      .retrieve()
                      .toEntity(Void.class)
                      .getBody()
              )
              .toEither()
              .bimap(Notification::create, Function.identity());
   }

   @Override
   public Either<Notification, Void> deleteUser(@NonNull String userId) {
      final TokenClientKeycloak tokenClient = this.loginClientKeycloakService.getTokenClient();
      final String uri = "/users/%s".formatted(userId);
      final String token = "Bearer %s".formatted(tokenClient.access_token());

      return API.Try(() -> this.restClient
                      .delete()
                      .uri(keycloakUrlBase + uri)
                      .headers(headers -> {
                         headers.add(HttpHeaders.AUTHORIZATION, token);
                         headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
                      })
                      .retrieve()
                      .toEntity(Void.class)
                      .getBody()
              )
              .toEither()
              .bimap(Notification::create, Function.identity());
   }

   @Override
   public Either<Notification, Void> activateOrDeactivateUser(@NonNull String userId, @NonNull Boolean enabled) {
      final TokenClientKeycloak tokenClient = this.loginClientKeycloakService.getTokenClient();
      final String uri = "/users/%s".formatted(userId);
      final String token = "Bearer %s".formatted(tokenClient.access_token());
      final Map<String, Object> body = new HashMap<>();
      body.put("enabled", enabled);

      return API.Try(() -> this.restClient
                      .put()
                      .uri(keycloakUrlBase + uri)
                      .headers(headers -> {
                         headers.add(HttpHeaders.AUTHORIZATION, token);
                         headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
                      })
                      .body(body)
                      .retrieve()
                      .toEntity(Void.class)
                      .getBody()
              )
              .toEither()
              .bimap(Notification::create, Function.identity());
   }

   @Override
   public Either<Notification, Pagination<UserInformationKeycloak>> findUsers(final SearchQuery searchQuery) {
      final TokenClientKeycloak tokenClient = this.loginClientKeycloakService.getTokenClient();
      final String token = "Bearer %s".formatted(tokenClient.access_token());
      final String uri = "/users";
      final String filter = uri + "?%s".formatted(UtilsMethods.createFilter.apply(searchQuery.terms()));
      final String count = uri + "/count?%s".formatted(UtilsMethods.createFilter.apply(searchQuery.terms()));

      return API.Try(() -> {
                 final List<UserInformationKeycloak> users = Objects.requireNonNull(this.restClient
                                 .get()
                                 .uri(keycloakUrlBase + filter)
                                 .headers(headers -> {
                                    headers.add(HttpHeaders.AUTHORIZATION, token);
                                    headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
                                 })
                                 .retrieve()
                                 .body(new ParameterizedTypeReference<List<UserInformationKeycloak>>() {
                                 }))
                         .stream()
                         .sorted(Comparator.comparing(UserInformationKeycloak::firstName))
                         .toList();

                 final Long countUser = this.restClient
                         .get()
                         .uri(keycloakUrlBase + count)
                         .headers(headers -> {
                            headers.add(HttpHeaders.AUTHORIZATION, token);
                            headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
                         })
                         .retrieve()
                         .body(Long.class);

                 return Pagination.create(users, searchQuery.currentPage(), searchQuery.itemsPerPage(), countUser);
              })
              .toEither()
              .bimap(Notification::create, Function.identity());
   }
}
