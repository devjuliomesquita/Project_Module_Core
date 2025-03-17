package com.juliomesquita.core.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.juliomesquita.core.services.dtos.KeycloakUserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
public class KeycloakServicesImpl implements KeycloakService {
   private static final ObjectMapper mapper = new ObjectMapper();
   @Value("${configs.keycloak.auth-server-url}")
   private String keycloakUrl;

   @Value("${configs.keycloak.realm}")
   private String realm;

   @Value("${configs.keycloak.client-id}")
   private String clientId;

   @Value("${configs.keycloak.client-secret}")
   private String clientSecret;

   private final RestClient restClient;

   public KeycloakServicesImpl(RestClient restClient) {
      this.restClient = restClient;
   }

   // 🔹 Criar usuário no Keycloak
//   public ResponseEntity<String> createUser(Map<String, Object> user) {
//      String url = keycloakUrl + "/admin/realms/" + realm + "/users";
//      return restClient.post()
//              .uri(url)
//              .headers(this::getAdminHeaders)
//              .contentType(MediaType.APPLICATION_JSON)
//              .body(user)
//              .retrieve()
//              .toEntity(String.class);
//   }

   private void getAdminHeaders(HttpHeaders headers) {
      headers.setContentType(MediaType.APPLICATION_JSON);
//      headers.setBearerAuth(getAdminToken());
   }

   private String getAdminToken() {
      String url = keycloakUrl + "/realms/master/protocol/openid-connect/token";

      Map<String, String> body = Map.of(
              "client_id",
              "admin-cli",
              "grant_type",
              "password",
              "username",
              "admin",
              "password",
              "admin"
      );

      try {
         Map<String, Object> response = restClient.post()
                 .uri(url)
                 .headers(headers -> headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED))
                 .body(body)
                 .retrieve()
                 .body(Map.class);

         return response.get("access_token").toString();
      } catch (HttpClientErrorException e) {
         throw new RuntimeException("Erro ao obter token do admin: " + e.getResponseBodyAsString());
      }
   }

   @Override
   public String createUser(KeycloakUserDto data) {
//      Map<String, Object> dataConverted = mapper.convertValue(
//              data,
//              Map.class
//      );
      String url = keycloakUrl + "/admin/realms/" + realm + "/users";
      return restClient.post()
              .uri(url)
              .headers(this::getAdminHeaders)
              .contentType(MediaType.APPLICATION_JSON)
              .body(data)
              .retrieve()
              .toEntity(String.class)
              .getBody();

   }

   @Override
   public Map<String, Object> loginUser(String username, String password) {
      return Map.of();
   }

   @Override
   public String logoutUser(String refreshToken) {
      return "";
   }
}
