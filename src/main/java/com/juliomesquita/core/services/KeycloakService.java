package com.juliomesquita.core.services;

import com.juliomesquita.core.services.dtos.KeycloakUserDto;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

public interface KeycloakService {
   String createUser(KeycloakUserDto data);

   Map<String, Object> loginUser(String username, String password);

   String logoutUser(String refreshToken);
}
