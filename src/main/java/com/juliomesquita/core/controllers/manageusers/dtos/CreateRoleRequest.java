package com.juliomesquita.core.controllers.manageusers.dtos;

public record CreateRoleRequest(
        String name,
        String description
) {
   public static final String exampleRequest = """
           {
           "name":"Role name",
           "description":"Role description"
           }
           """;
}
