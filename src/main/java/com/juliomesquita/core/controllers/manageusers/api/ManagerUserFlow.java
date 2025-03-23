package com.juliomesquita.core.controllers.manageusers.api;

import com.juliomesquita.core.controllers.manageusers.dtos.*;
import com.juliomesquita.core.controllers.shared.DefaultAuthAPIResponses;
import com.juliomesquita.core.controllers.shared.DefaultPublicAPIResponses;
import com.juliomesquita.core.shared.pagination.Pagination;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@Tag(name = "Manager Users", description = "API de gerenciamento de usuários.")
public interface ManagerUserFlow {
   @Operation(
           operationId = "createUser",
           summary = "Create user.",
           description = "This endpoint receives user data for creation.",
           tags = {"Manager Users"},
           responses = @ApiResponse(responseCode = "201", description = "Created"),
           requestBody = @RequestBody(content = {@Content(examples = @ExampleObject(value = CreateUserRequest.exampleRequest))})
   )
   @DefaultPublicAPIResponses
   @PostMapping("/users")
   ResponseEntity<?> createUser(@RequestBody CreateUserRequest request);

   @Operation(
           operationId = "updateUser",
           summary = "Update user data.",
           description = "This endpoint receives user data for update.",
           tags = {"Manager Users"},
           responses = @ApiResponse(responseCode = "200", description = "OK"),
           requestBody = @RequestBody(content = {@Content(examples = @ExampleObject(value = UpdateUserRequest.exampleRequest))})
   )
   @DefaultAuthAPIResponses
   @PutMapping("/users/{userId}")
   ResponseEntity<?> updateUser(
           @PathVariable(name = "userId") UUID userId,
           @RequestBody UpdateUserRequest request
   );

   @Operation(
           operationId = "deleteUser",
           summary = "Delete user.",
           description = "This endpoint receives userId for deleted.",
           tags = {"Manager Users"},
           responses = @ApiResponse(responseCode = "204", description = "No Content")
   )
   @DefaultAuthAPIResponses
   @DeleteMapping("/users/{userId}")
   ResponseEntity<?> deleteUser(@PathVariable(name = "userId") UUID userId);

   @Operation(
           operationId = "activateOrDeactivateUser",
           summary = "Activate or deactivate user.",
           description = "This endpoint receives userId and data for user.",
           tags = {"Manager Users"},
           responses = @ApiResponse(responseCode = "200", description = "OK")
   )
   @DefaultAuthAPIResponses
   @PutMapping("/users/{userId}/enable")
   ResponseEntity<?> activateOrDeactivateUser(
           @PathVariable(name = "userId") UUID userId,
           @RequestBody UserStatusRequest request
   );

   @Operation(
           operationId = "findUsers",
           summary = "Find all users.",
           description = "This endpoint returns all users.",
           tags = {"Manager Users"},
           responses = @ApiResponse(responseCode = "200", description = "Ok", content = {@Content(schema = @Schema(implementation = Pagination.class))})
   )
   @DefaultAuthAPIResponses
   @GetMapping("/users")
   ResponseEntity<?> findUsers(
           @RequestParam(name = "currentPage", defaultValue = "0") Integer currentPage,
           @RequestParam(name = "itemsPerPage", defaultValue = "10") Integer itemsPerPage,
           @RequestParam(name = "terms", defaultValue = "") String terms,
           @RequestParam(name = "sort") String sort,
           @RequestParam(name = "direction") String direction
   );

   @Operation(
           operationId = "resetPassword",
           summary = "Reset User password.",
           description = "This endpoint receives new password and userId.",
           tags = {"Manager Users"},
           responses = @ApiResponse(responseCode = "200", description = "OK", content = {@Content(schema = @Schema(implementation = UserLoginResponse.class))}),
           requestBody = @RequestBody(content = {@Content(examples = @ExampleObject(value = ResetPasswordRequest.exampleRequest))})
   )
   @DefaultPublicAPIResponses
   @PutMapping("/users/{userId}/reset-password")
   ResponseEntity<?> resetPassword(
           @PathVariable(name = "userId") UUID userId,
           @RequestBody ResetPasswordRequest request
   );
}
