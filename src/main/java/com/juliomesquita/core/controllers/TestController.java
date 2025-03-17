package com.juliomesquita.core.controllers;

import com.juliomesquita.core.services.KeycloakService;
import com.juliomesquita.core.services.dtos.KeycloakUserDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class TestController {
   private final KeycloakService keycloakService;

   public TestController(KeycloakService keycloakService) {
      this.keycloakService = keycloakService;
   }

   @PostMapping("/auth")
   public String endpointPublic(@RequestBody KeycloakUserDto userDto){
      return this.keycloakService.createUser(userDto);
   }

   @GetMapping("/user")
   public String endpointPrivado(){
      return "Endpoint de private";
   }
}
