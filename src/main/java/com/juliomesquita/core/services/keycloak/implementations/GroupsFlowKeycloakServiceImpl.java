package com.juliomesquita.core.services.keycloak.implementations;

import com.juliomesquita.core.services.keycloak.dtos.groupflow.GroupDataKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.loginflow.TokenClientKeycloak;
import com.juliomesquita.core.services.keycloak.interfaces.GroupsFlowKeycloakService;
import com.juliomesquita.core.services.keycloak.interfaces.LoginClientKeycloakService;
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

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Component
public class GroupsFlowKeycloakServiceImpl implements GroupsFlowKeycloakService {
   @Value("${configs.keycloak.auth-server-url-admin}")
   private String keycloakUrlBase;

   private final RestClient restClient;
   private final LoginClientKeycloakService loginClientKeycloakService;

   public GroupsFlowKeycloakServiceImpl(
           final RestClient restClient,
           final LoginClientKeycloakService loginClientKeycloakService
   ) {
      this.restClient = Objects.requireNonNull(restClient);
      this.loginClientKeycloakService = Objects.requireNonNull(loginClientKeycloakService);
   }

   @Override
   public Either<Notification, String> createGroup(@NonNull String groupName) {
      final TokenClientKeycloak tokenClient = this.loginClientKeycloakService.getTokenClient();
      final String uri = "/groups";
      final String token = "Bearer %s".formatted(tokenClient.access_token());
      final Map<String, Object> body = new HashMap<>();
      body.put("name", groupName);

      return API.Try(() -> {
                         final URI location = this.restClient
                                 .post()
                                 .uri(keycloakUrlBase + uri)
                                 .headers(headers -> {
                                    headers.add(HttpHeaders.AUTHORIZATION, token);
                                    headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
                                 })
                                 .body(body)
                                 .retrieve()
                                 .toBodilessEntity()
                                 .getHeaders()
                                 .getLocation();
                         return Objects.requireNonNull(location).toString();
                      }
              )
              .toEither()
              .bimap(Notification::create, UtilsMethods.extractId);

   }

   @Override
   public Either<Notification, Void> updateGroup(@NonNull String id, @NonNull String groupName) {
      final TokenClientKeycloak tokenClient = this.loginClientKeycloakService.getTokenClient();
      final String uri = "/groups/%s".formatted(id);
      final String token = "Bearer %s".formatted(tokenClient.access_token());
      final Map<String, Object> body = new HashMap<>();
      body.put("name", groupName);

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
   public Either<Notification, Void> deleteGroup(@NonNull String id) {
      final TokenClientKeycloak tokenClient = this.loginClientKeycloakService.getTokenClient();
      final String uri = "/groups/%s".formatted(id);
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
   public Either<Notification, List<GroupDataKeycloak>> findGroups() {
      final TokenClientKeycloak tokenClient = this.loginClientKeycloakService.getTokenClient();
      final String uri = "/groups";
      final String token = "Bearer %s".formatted(tokenClient.access_token());

      return API.Try(() -> this.restClient
                      .get()
                      .uri(keycloakUrlBase + uri)
                      .headers(headers -> {
                         headers.add(HttpHeaders.AUTHORIZATION, token);
                         headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
                      })
                      .retrieve()
                      .body(new ParameterizedTypeReference<List<GroupDataKeycloak>>() {
                      })
              )
              .toEither()
              .bimap(Notification::create, Function.identity());
   }
}
