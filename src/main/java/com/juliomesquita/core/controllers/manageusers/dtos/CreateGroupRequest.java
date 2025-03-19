package com.juliomesquita.core.controllers.manageusers.dtos;

public record CreateGroupRequest(
        String name
) {
   public static final String exampleRequest = """
           {
           "name":"Group name"
           }
           """;
}
