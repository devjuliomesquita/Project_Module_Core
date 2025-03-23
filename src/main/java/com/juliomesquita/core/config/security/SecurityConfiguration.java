package com.juliomesquita.core.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Objects;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {
   private final JwtAuthenticationConverter jwtAuthenticationConverter;
   private final SecurityCorsConfiguration securityCorsConfiguration;

   public SecurityConfiguration(
           final JwtAuthenticationConverter jwtAuthenticationConverter,
           final SecurityCorsConfiguration securityCorsConfiguration
   ) {
      this.jwtAuthenticationConverter = Objects.requireNonNull(jwtAuthenticationConverter);
      this.securityCorsConfiguration = Objects.requireNonNull(securityCorsConfiguration);
   }

   @Bean
   public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
      return http
              .csrf(AbstractHttpConfigurer::disable)
              .cors(cors -> cors.configurationSource(this.securityCorsConfiguration.corsConfigurationSource()))
//              .httpBasic(Customizer.withDefaults())
              .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
              .oauth2ResourceServer(oauth2 ->
                      oauth2.jwt(jwt ->
                              jwt.jwtAuthenticationConverter(this.jwtAuthenticationConverter)))
              .authorizeHttpRequests(request -> request
                      .requestMatchers("/", "/manager-user/auth/**")
                      .permitAll()
                      .requestMatchers(HttpMethod.POST, "/manager-user/users")
                      .permitAll()
                      .requestMatchers(HttpMethod.PUT, "/manager-user/users/*/reset-password")
                      .permitAll()
                      .requestMatchers("/swagger-ui.html/**", "/swagger-ui/**", "/v3/api-docs/**")
                      .permitAll()
                      .anyRequest()
                      .authenticated()
              )
              .build();
   }
}
