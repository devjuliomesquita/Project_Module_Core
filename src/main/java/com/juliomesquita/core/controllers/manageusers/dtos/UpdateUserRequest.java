package com.juliomesquita.core.controllers.manageusers.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdateUserRequest(
        @JsonProperty("firstName") String firstName,
        @JsonProperty("lastName") String lastName,
        @JsonProperty("email") String email
) {
   public static final String exampleRequest = """
           {
           	"firstName": "Usuário",
           	"lastName": "Sobrenome usuário",
           	"email": "novoemail@email.com"
           }
           """;
}
