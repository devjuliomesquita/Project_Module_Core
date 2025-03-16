package com.juliomesquita.core.config.security;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class JwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

   private static final String RESOURCE_ACCESS = "resource_access";
   private static final String ROLES = "roles";
   private static final String ROLE_PRE_FIX = "ROLE_";
   private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter =
           new JwtGrantedAuthoritiesConverter();

   @Value("${jwt.auth.converter.principle-attribute}")
   private String principleAttribute;

   @Value("${jwt.auth.converter.resource-id}")
   private String resourceId;


   @Override
   public AbstractAuthenticationToken convert(final @NonNull Jwt jwt) {
      Collection<GrantedAuthority> authorities =
              Stream.concat(
                      jwtGrantedAuthoritiesConverter.convert(jwt).stream(),
                      extractResourceRoles(jwt).stream()
              ).collect(Collectors.toSet());
      return new JwtAuthenticationToken(
              jwt,
              authorities,
              this.getPrincipleClaimName(jwt)
      );
   }

   @Override
   public <U> Converter<Jwt, U> andThen(Converter<? super AbstractAuthenticationToken, ? extends U> after) {
      return Converter.super.andThen(after);
   }

   private String getPrincipleClaimName(final @NonNull Jwt jwt) {
      String claimName = JwtClaimNames.SUB;
      if (principleAttribute != null) {
         claimName = principleAttribute;
      }
      return jwt.getClaim(claimName);
   }

   private Collection<? extends GrantedAuthority> extractResourceRoles(final @NonNull Jwt jwt) {
      final Map<String, Object> resourceAccess;
      final Map<String, Object> resource;
      final Collection<String> resourceRoles;
      if (jwt.getClaim(RESOURCE_ACCESS) == null) {
         return Set.of();
      }
      resourceAccess = jwt.getClaim(RESOURCE_ACCESS);

      if (resourceAccess.get(resourceId) == null) {
         return Set.of();
      }
      resource = (Map<String, Object>) resourceAccess.get(resourceId);

      resourceRoles = (Collection<String>) resource.get(ROLES);

      return resourceRoles.stream()
              .map(role -> new SimpleGrantedAuthority(ROLE_PRE_FIX + role))
              .collect(Collectors.toSet());

   }
}
