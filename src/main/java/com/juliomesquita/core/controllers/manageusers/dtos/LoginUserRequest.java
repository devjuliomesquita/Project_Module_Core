package com.juliomesquita.core.controllers.manageusers.dtos;

public record LoginUserRequest(
        String username,
        String password
) {

   public static final String exampleRequest = """
           {
           "username":"johndoe",
           "password":"teste"
           }
           """;
}
