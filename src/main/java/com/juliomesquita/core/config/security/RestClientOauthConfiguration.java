package com.juliomesquita.core.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.client.OAuth2ClientHttpRequestInterceptor;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientOauthConfiguration {

//   @Bean
   public RestClient keycloakRestClientOAuth(
           RestClient.Builder builder,
           OAuth2AuthorizedClientManager manager
   ) {
      final OAuth2ClientHttpRequestInterceptor interceptor = new OAuth2ClientHttpRequestInterceptor(manager);
      interceptor.setClientRegistrationIdResolver((HttpRequest request) -> "keycloak");
      return builder.requestInterceptor(interceptor).build();
   }

   @Bean
   public RestClient restClient(){
      return RestClient.builder().build();
   }
}
