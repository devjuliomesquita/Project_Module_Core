package com.juliomesquita.core.controllers.manageusers.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateUserRequest(
        @JsonProperty("username") String username,
        @JsonProperty("email") String email,
        @JsonProperty("firstName") String firstName,
        @JsonProperty("lastName") String lastName,
        @JsonProperty("enabled") Boolean enabled,
        @JsonProperty("emailVerified") Boolean emailVerified
) {
   public static final String exampleRequest = """
           {
             "username": "johndoe",
             "email": "johndoe@example.com",
             "firstName": "John",
             "lastName": "Doe",
             "enabled": true,
             "emailVerified": false
           }
           """;
}
