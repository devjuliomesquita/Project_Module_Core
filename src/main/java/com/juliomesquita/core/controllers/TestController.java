package com.juliomesquita.core.controllers;

import com.juliomesquita.core.services.keycloak.KeycloakFacade;
import com.juliomesquita.core.services.keycloak.dtos.loginflow.CreateUserKeycloak;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class TestController {
   private final KeycloakFacade keycloakFacade;

   public TestController(KeycloakFacade keycloakFacade) {
      this.keycloakFacade = keycloakFacade;
   }

   @PostMapping("/auth")
   public String endpointPublic(@RequestBody CreateUserKeycloak userDto){
      return "";
//      return this.keycloakFacade.createUser(userDto);
   }

   @GetMapping("/user")
   public String endpointPrivado(){
      return "Endpoint de private";
   }
}
