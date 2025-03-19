package com.juliomesquita.core.controllers.manageusers.controllers;

import com.juliomesquita.core.controllers.manageusers.api.ManagerUsersAPI;
import com.juliomesquita.core.controllers.manageusers.dtos.CreateUserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ManagerUsersController implements ManagerUsersAPI {
   @Override
   public ResponseEntity<?> createUser(@RequestBody CreateUserRequest request) {
      return null;
   }
}
