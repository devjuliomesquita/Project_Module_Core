package com.juliomesquita.core.services.keycloak.implementations;

import com.juliomesquita.core.services.keycloak.dtos.associateflow.AssociateRolesKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.loginflow.TokenClientKeycloak;
import com.juliomesquita.core.services.keycloak.interfaces.AssociateRolesService;
import com.juliomesquita.core.services.keycloak.interfaces.LoginClientKeycloakService;
import com.juliomesquita.core.shared.validations.Notification;
import io.vavr.API;
import io.vavr.control.Either;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

@Component
public class AssociateRolesServiceImpl implements AssociateRolesService {
   @Value("${configs.keycloak.auth-server-url-admin}")
   private String keycloakUrlBase;

   private final RestClient restClient;
   private final LoginClientKeycloakService loginClientKeycloakService;

   public AssociateRolesServiceImpl(
           final RestClient restClient,
           final LoginClientKeycloakService loginClientKeycloakService
   ) {
      this.restClient = Objects.requireNonNull(restClient);
      this.loginClientKeycloakService = Objects.requireNonNull(loginClientKeycloakService);
   }

   @Override
   public Either<Notification, Void> associateRoleUser(@NonNull String userId, @NonNull List<AssociateRolesKeycloak> listRoles) {
      final TokenClientKeycloak tokenClient = this.loginClientKeycloakService.getTokenClient();
      final String uri = "/users/%s/role-mappings/realm".formatted(userId);
      final String token = "Bearer %s".formatted(tokenClient.access_token());

      return API.Try(() -> this.restClient
                      .post()
                      .uri(keycloakUrlBase + uri)
                      .headers(headers -> {
                         headers.add(HttpHeaders.AUTHORIZATION, token);
                         headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
                      })
                      .body(listRoles)
                      .retrieve()
                      .toEntity(Void.class)
                      .getBody()

              )
              .toEither()
              .bimap(Notification::create, Function.identity());
   }

   @Override
   public Either<Notification, Void> disassociateRoleUser(@NonNull String userId, @NonNull List<AssociateRolesKeycloak> listRoles) {
      final TokenClientKeycloak tokenClient = this.loginClientKeycloakService.getTokenClient();
      final String uri = "/users/%s/role-mappings/realm".formatted(userId);

      final HttpHeaders headers = new HttpHeaders();
      headers.setBearerAuth(tokenClient.access_token());
      headers.setContentType(MediaType.APPLICATION_JSON);

      final HttpEntity<List<AssociateRolesKeycloak>> req = new HttpEntity<>(listRoles, headers);

      return API.Try(() -> new RestTemplate()
                      .exchange(keycloakUrlBase + uri, HttpMethod.DELETE, req, Void.class)
                      .getBody()
              )
              .toEither()
              .bimap(Notification::create, Function.identity());
   }

   @Override
   public Either<Notification, Void> associateUserGroup(@NonNull String userId, @NonNull String groupId) {
      final TokenClientKeycloak tokenClient = this.loginClientKeycloakService.getTokenClient();
      final String uri = "/groups/%s/members/%s".formatted(groupId, userId);
      final String token = "Bearer %s".formatted(tokenClient.access_token());

      return API.Try(() -> this.restClient
                      .post()
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
   public Either<Notification, Void> disassociateUserGroup(@NonNull String userId, @NonNull String groupId) {
      final TokenClientKeycloak tokenClient = this.loginClientKeycloakService.getTokenClient();
      final String uri = "/groups/%s/members/%s".formatted(groupId, userId);
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
   public Either<Notification, Void> associateRoleGroup(@NonNull String groupId, @NonNull List<AssociateRolesKeycloak> listRoles) {
      final TokenClientKeycloak tokenClient = this.loginClientKeycloakService.getTokenClient();
      final String uri = "/groups/%s/role-mappings/realm".formatted(groupId);
      final String token = "Bearer %s".formatted(tokenClient.access_token());

      return API.Try(() -> this.restClient
                      .post()
                      .uri(keycloakUrlBase + uri)
                      .headers(headers -> {
                         headers.add(HttpHeaders.AUTHORIZATION, token);
                         headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
                      })
                      .body(listRoles)
                      .retrieve()
                      .toEntity(Void.class)
                      .getBody()
              )
              .toEither()
              .bimap(Notification::create, Function.identity());
   }

   @Override
   public Either<Notification, Void> disassociateRoleGroup(@NonNull String groupId, @NonNull List<AssociateRolesKeycloak> listRoles) {
      final TokenClientKeycloak tokenClient = this.loginClientKeycloakService.getTokenClient();
      final String uri = "/groups/%s/role-mappings/realm".formatted(groupId);

      final HttpHeaders headers = new HttpHeaders();
      headers.setBearerAuth(tokenClient.access_token());
      headers.setContentType(MediaType.APPLICATION_JSON);

      final HttpEntity<List<AssociateRolesKeycloak>> req = new HttpEntity<>(listRoles, headers);

      return API.Try(() -> new RestTemplate()
                      .exchange(keycloakUrlBase + uri, HttpMethod.DELETE, req, Void.class)
                      .getBody()
              )
              .toEither()
              .bimap(Notification::create, Function.identity());
   }
}
