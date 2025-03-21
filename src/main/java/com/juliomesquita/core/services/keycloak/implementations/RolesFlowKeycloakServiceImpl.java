package com.juliomesquita.core.services.keycloak.implementations;

import com.juliomesquita.core.services.keycloak.dtos.loginflow.TokenClientKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.roleflow.CreateRoleKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.roleflow.RoleDataKeycloak;
import com.juliomesquita.core.services.keycloak.interfaces.LoginClientKeycloakService;
import com.juliomesquita.core.services.keycloak.interfaces.RolesFlowKeycloakService;
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
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

@Component
public class RolesFlowKeycloakServiceImpl implements RolesFlowKeycloakService {
   @Value("${configs.keycloak.auth-server-url-admin}")
   private String keycloakUrlBase;

   private final RestClient restClient;
   private final LoginClientKeycloakService loginClientKeycloakService;

   public RolesFlowKeycloakServiceImpl(
           final RestClient restClient,
           final LoginClientKeycloakService loginClientKeycloakService
   ) {
      this.restClient = Objects.requireNonNull(restClient);
      this.loginClientKeycloakService = Objects.requireNonNull(loginClientKeycloakService);
   }

   @Override
   public Either<Notification, String> createRole(@NonNull CreateRoleKeycloak data) {
      final TokenClientKeycloak tokenClient = this.loginClientKeycloakService.getTokenClient();
      final String uri = "/roles";
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
              )
              .toEither()
              .bimap(Notification::create, UtilsMethods.extractId);

   }

   @Override
   public Either<Notification, Void> updateRole(@NonNull String roleName, @NonNull CreateRoleKeycloak data) {
      final TokenClientKeycloak tokenClient = this.loginClientKeycloakService.getTokenClient();
      final String uri = "/roles/%s".formatted(roleName);
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
   public Either<Notification, Void> deleteRole(String roleName) {
      final TokenClientKeycloak tokenClient = this.loginClientKeycloakService.getTokenClient();
      final String uri = "/roles/%s".formatted(roleName);
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
   public Either<Notification, List<RoleDataKeycloak>> findRoles() {
      final TokenClientKeycloak tokenClient = this.loginClientKeycloakService.getTokenClient();
      final String uri = "/roles";
      final String token = "Bearer %s".formatted(tokenClient.access_token());

      return API.Try(() -> this.restClient
                      .get()
                      .uri(keycloakUrlBase + uri)
                      .headers(headers -> {
                         headers.add(HttpHeaders.AUTHORIZATION, token);
                         headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
                      })
                      .retrieve()
                      .body(new ParameterizedTypeReference<List<RoleDataKeycloak>>() {
                      })
              )
              .toEither()
              .bimap(Notification::create, Function.identity());

   }
}
