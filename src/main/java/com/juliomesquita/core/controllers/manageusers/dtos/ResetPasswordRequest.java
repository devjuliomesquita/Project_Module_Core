package com.juliomesquita.core.controllers.manageusers.dtos;

public record ResetPasswordRequest(
        String password,
        Boolean temporary
) {
   public static final String exampleRequest = """
           {
           "password": "newPassword",
           "temporary": false
           }
           """;
}
