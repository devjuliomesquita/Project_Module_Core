package com.juliomesquita.core.controllers.manageusers.api;

import com.juliomesquita.core.controllers.manageusers.dtos.CreateRoleRequest;
import com.juliomesquita.core.controllers.manageusers.dtos.ListRolesInfoResponse;
import com.juliomesquita.core.controllers.shared.DefaultAuthAPIResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Manager Roles", description = "API de gerenciamento de permissões.")
public interface ManagerRoleFlow {
   @Operation(
           operationId = "createRole",
           summary = "Create role.",
           description = "This endpoint receives roles data for create.",
           tags = {"Manager Roles"},
           responses = @ApiResponse(responseCode = "201", description = "Created"),
           requestBody = @RequestBody(content = {@Content(examples = @ExampleObject(value = CreateRoleRequest.exampleRequest))}),
           security = @SecurityRequirement(name = "bearerAuth")
   )
   @DefaultAuthAPIResponses
   @PostMapping("/roles")
   ResponseEntity<?> createRole(@RequestBody CreateRoleRequest request);

   @Operation(
           operationId = "updateRole",
           summary = "Update role.",
           description = "This endpoint receives roleName and data for role.",
           tags = {"Manager Roles"},
           responses = @ApiResponse(responseCode = "200", description = "OK"),
           requestBody = @RequestBody(content = {@Content(examples = @ExampleObject(value = CreateRoleRequest.exampleRequest))}),
           security = @SecurityRequirement(name = "bearerAuth")
   )
   @DefaultAuthAPIResponses
   @PutMapping("/roles/{roleName}")
   ResponseEntity<?> updateRole(
           @PathVariable(name = "roleName") String roleName,
           @RequestBody CreateRoleRequest request
   );

   @Operation(
           operationId = "deleteRole",
           summary = "Delete Role.",
           description = "This endpoint receives roleName for delete.",
           tags = {"Manager Roles"},
           responses = @ApiResponse(responseCode = "204", description = "No Content"),
           security = @SecurityRequirement(name = "bearerAuth")
   )
   @DefaultAuthAPIResponses
   @DeleteMapping("/roles/{roleName}")
   ResponseEntity<?> deleteRole(
           @PathVariable(name = "roleName") String roleName
   );

   @Operation(
           operationId = "findRoles",
           summary = "Find all roles.",
           description = "This endpoint returns all roles.",
           tags = {"Manager Roles"},
           responses = @ApiResponse(responseCode = "200", description = "OK", content = {@Content(schema = @Schema(implementation = ListRolesInfoResponse.class))}),
           security = @SecurityRequirement(name = "bearerAuth")
   )
   @DefaultAuthAPIResponses
   @GetMapping("/roles")
   ResponseEntity<?> findRoles();
}
