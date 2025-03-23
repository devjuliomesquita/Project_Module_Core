package com.juliomesquita.core.controllers.manageusers.api;

import com.juliomesquita.core.controllers.manageusers.dtos.CreateGroupRequest;
import com.juliomesquita.core.controllers.manageusers.dtos.ListGroupsInfosResponse;
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

import java.util.UUID;

@Tag(name = "Manager Groups", description = "API de gerenciamento de grupos.")
public interface ManagerGroupFlow {
   @Operation(
           operationId = "createGroup",
           summary = "Create group.",
           description = "This endpoint receives group data for create.",
           tags = {"Manager Groups"},
           responses = @ApiResponse(responseCode = "201", description = "Created"),
           requestBody = @RequestBody(content = {@Content(examples = @ExampleObject(value = CreateGroupRequest.exampleRequest))}),
           security = @SecurityRequirement(name = "bearerAuth")
   )
   @DefaultAuthAPIResponses
   @PostMapping("/groups")
   ResponseEntity<?> createGroup(@RequestBody CreateGroupRequest request);

   @Operation(
           operationId = "updateGroup",
           summary = "Update group.",
           description = "This endpoint receives and data for group.",
           tags = {"Manager Groups"},
           responses = @ApiResponse(responseCode = "200", description = "OK"),
           requestBody = @RequestBody(content = {@Content(examples = @ExampleObject(value = CreateGroupRequest.exampleRequest))}),
           security = @SecurityRequirement(name = "bearerAuth")
   )
   @DefaultAuthAPIResponses
   @PutMapping("/groups/{groupId}")
   ResponseEntity<?> updateGroup(
           @PathVariable(name = "groupId") UUID groupId,
           @RequestBody CreateGroupRequest request
   );

   @Operation(
           operationId = "deleteGroup",
           summary = "Delete Group.",
           description = "This endpoint receives groupId for delete.",
           tags = {"Manager Groups"},
           responses = @ApiResponse(responseCode = "204", description = "No Content"),
           security = @SecurityRequirement(name = "bearerAuth")
   )
   @DefaultAuthAPIResponses
   @DeleteMapping("/groups/{groupId}")
   ResponseEntity<?> deleteGroup(
           @PathVariable(name = "groupId") UUID groupId
   );

   @Operation(
           operationId = "findGroups",
           summary = "Find all groups.",
           description = "This endpoint returns all groups.",
           tags = {"Manager Groups"},
           responses = @ApiResponse(responseCode = "200", description = "OK", content = {@Content(schema = @Schema(implementation = ListGroupsInfosResponse.class))}),
           security = @SecurityRequirement(name = "bearerAuth")
   )
   @DefaultAuthAPIResponses
   @GetMapping("/groups")
   ResponseEntity<?> findGroups();
}
