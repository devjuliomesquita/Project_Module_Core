package com.juliomesquita.core.controllers.manageusers.api;

import com.juliomesquita.core.controllers.manageusers.dtos.ListRolesRequest;
import com.juliomesquita.core.controllers.shared.DefaultAuthAPIResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Tag(name = "Manager Associations", description = "API de gerenciamento de associações.")
public interface ManagerAssociationFlow {
   @Operation(
           operationId = "associateOrDisassociateRoleUser",
           summary = "Associate or disassociate Role a with user.",
           description = "This endpoint receives userId and roles data for associate.",
           tags = {"Manager Associations"},
           responses = @ApiResponse(responseCode = "200", description = "Ok"),
           requestBody = @RequestBody(content = {@Content(examples = @ExampleObject(value = ListRolesRequest.exampleRequest))}),
           security = @SecurityRequirement(name = "bearerAuth")
   )
   @DefaultAuthAPIResponses
   @PostMapping("/associations/user/{userId}/role")
   ResponseEntity<?> associateOrDisassociateRoleUser(
           @PathVariable(name = "userId") UUID userId,
           @RequestBody ListRolesRequest request
   );

   @Operation(
           operationId = "associateOrDisassociateRoleGroup",
           summary = "Associate or disassociate Role a with group.",
           description = "This endpoint receives groupId and roles data for associate.",
           tags = {"Manager Associations"},
           responses = @ApiResponse(responseCode = "200", description = "Ok"),
           requestBody = @RequestBody(content = {@Content(examples = @ExampleObject(value = ListRolesRequest.exampleRequest))}),
           security = @SecurityRequirement(name = "bearerAuth")
   )
   @DefaultAuthAPIResponses
   @PostMapping("/associations/group/{groupId}/role")
   ResponseEntity<?> associateOrDisassociateRoleGroup(
           @PathVariable(name = "groupId") UUID groupId,
           @RequestBody ListRolesRequest request
   );

   @Operation(
           operationId = "associateOrDisassociateUserGroup",
           summary = "Associate or disassociate User a with group.",
           description = "This endpoint receives groupId and userId for associate.",
           tags = {"Manager Associations"},
           responses = @ApiResponse(responseCode = "200", description = "Ok"),
           security = @SecurityRequirement(name = "bearerAuth")
   )
   @DefaultAuthAPIResponses
   @PostMapping("/associations/group/{groupId}/user/{userId}")
   ResponseEntity<?> associateOrDisassociateUserGroup(
           @PathVariable(name = "groupId") UUID groupId,
           @PathVariable(name = "userId") UUID userId,
           @RequestParam(name = "associate") Boolean associate
   );
}
