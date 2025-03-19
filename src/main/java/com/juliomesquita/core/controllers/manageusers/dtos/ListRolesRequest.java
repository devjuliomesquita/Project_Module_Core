package com.juliomesquita.core.controllers.manageusers.dtos;

import java.util.List;

public record ListRolesRequest(
        Boolean associate,
        List<RoleRequest> listRoles
) {
   public record RoleRequest(
           String id,
           String name
   ) {
   }

   public static final String exampleRequest = """
           {
              "associate": true,
              "listRoles": [
               {
                   "id": "7dff72ed-6c65-4aae-84fc-53123f4f5ba2",
                   "name": "Role 1"
               },
               {
                   "id": "2ef83c26-3ffb-4922-9d4b-c3db3468f68d",
                   "name": "Role 2"
               }
              ]
           }
           """;
}
