package com.juliomesquita.core.controllers.manageusers.api;

import com.juliomesquita.core.controllers.manageusers.dtos.*;
import com.juliomesquita.core.controllers.shared.DefaultAuthAPIResponses;
import com.juliomesquita.core.controllers.shared.DefaultPublicAPIResponses;
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

@RequestMapping(value = "/manager-user")
@Tag(name = "Manager Users", description = "API de gerenciamento de usuários.")
public interface ManagerUsersAPI {
   @Operation(
           operationId = "createUser",
           summary = "Create user.",
           description = "This endpoint receives user data for creation.",
           tags = "Manager Users",
           responses = @ApiResponse(responseCode = "201", description = "Created"),
           requestBody = @RequestBody(content = {@Content(examples = @ExampleObject(value = CreateUserRequest.exampleRequest))})
   )
   @DefaultPublicAPIResponses
   @PostMapping("/users")
   ResponseEntity<?> createUser(@RequestBody CreateUserRequest request);

   @Operation(
           operationId = "loginUser",
           summary = "Login user.",
           description = "This endpoint receives username and password for login.",
           tags = "Manager Users",
           responses = @ApiResponse(responseCode = "200", description = "OK", content = {@Content(schema = @Schema(implementation = UserLoginResponse.class))}),
           requestBody = @RequestBody(content = {@Content(examples = @ExampleObject(value = LoginUserRequest.exampleRequest))})
   )
   @DefaultPublicAPIResponses
   @PostMapping("/login")
   ResponseEntity<?> loginUser(@RequestBody LoginUserRequest request);

   @Operation(
           operationId = "refreshToken",
           summary = "Refresh token user.",
           description = "This endpoint receives refresh token and returns new login token.",
           tags = "Manager Users",
           responses = @ApiResponse(responseCode = "200", description = "OK", content = {@Content(schema = @Schema(implementation = UserLoginResponse.class))})
   )
   @DefaultPublicAPIResponses
   @PostMapping("/refresh-token")
   ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest request);

   @Operation(
           operationId = "logoutUser",
           summary = "Logout user.",
           description = "This endpoint receives refresh token for logout.",
           tags = "Manager Users",
           responses = @ApiResponse(responseCode = "204", description = "No Content")
   )
   @DefaultPublicAPIResponses
   @PostMapping("/logout")
   ResponseEntity<?> logoutUser(@RequestBody RefreshTokenRequest request);

   @Operation(
           operationId = "resetPassword",
           summary = "Reset User password.",
           description = "This endpoint receives new password and userId.",
           tags = "Manager Users",
           responses = @ApiResponse(responseCode = "200", description = "OK", content = {@Content(schema = @Schema(implementation = UserLoginResponse.class))}),
           requestBody = @RequestBody(content = {@Content(examples = @ExampleObject(value = ResetPasswordRequest.exampleRequest))})
   )
   @DefaultPublicAPIResponses
   @PutMapping("/users/{userId}/reset-password")
   ResponseEntity<?> resetPassword(
           @PathVariable(name = "userId") UUID userId,
           @RequestBody ResetPasswordRequest request
   );

   @Operation(
           operationId = "updateUser",
           summary = "Update user data.",
           description = "This endpoint receives user data for update.",
           tags = "Manager Users",
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
           tags = "Manager Users",
           responses = @ApiResponse(responseCode = "204", description = "No Content")
   )
   @DefaultAuthAPIResponses
   @DeleteMapping("/users/{userId}")
   ResponseEntity<?> deleteUser(@PathVariable(name = "userId") UUID userId);

   @Operation(
           operationId = "activateOrDeactivateUser",
           summary = "Activate or deactivate user.",
           description = "This endpoint receives userId and data for user.",
           tags = "Manager Users",
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
           tags = "Manager Users",
           responses = @ApiResponse(responseCode = "200", description = "Ok", content = {@Content(schema = @Schema(implementation = ListUserInfosResponse.class))})
   )
   @DefaultAuthAPIResponses
   @GetMapping("/users")
   ResponseEntity<ListUserInfosResponse> findUsers();

   @Operation(
           operationId = "findUsersByFilter",
           summary = "Find all users by filter.",
           description = "This endpoint returns all users by filter.",
           tags = "Manager Users",
           responses = @ApiResponse(responseCode = "200", description = "Ok", content = {@Content(schema = @Schema(implementation = ListUserInfosResponse.class))})
   )
   @DefaultAuthAPIResponses
   @GetMapping("/users/filters")
   ResponseEntity<ListUserInfosResponse> findUsersByFilter(
           @RequestParam(name = "Email") String email,
           @RequestParam(name = "Enabled", defaultValue = "true", required = false) Boolean enabled
   );

   @Operation(
           operationId = "createRole",
           summary = "Create role.",
           description = "This endpoint receives roles data for create.",
           tags = "Manager Users",
           responses = @ApiResponse(responseCode = "201", description = "Created"),
           requestBody = @RequestBody(content = {@Content(examples = @ExampleObject(value = CreateRoleRequest.exampleRequest))})
   )
   @DefaultAuthAPIResponses
   @PostMapping("/role")
   ResponseEntity<?> createRole(@RequestBody CreateRoleRequest request);

   @Operation(
           operationId = "updateRole",
           summary = "Update role.",
           description = "This endpoint receives roleName and data for role.",
           tags = "Manager Users",
           responses = @ApiResponse(responseCode = "200", description = "OK"),
           requestBody = @RequestBody(content = {@Content(examples = @ExampleObject(value = CreateRoleRequest.exampleRequest))})
   )
   @DefaultAuthAPIResponses
   @PutMapping("/role/{roleName}")
   ResponseEntity<?> updateRole(
           @PathVariable(name = "roleName") String roleName,
           @RequestBody CreateRoleRequest request
   );

   @Operation(
           operationId = "deleteRole",
           summary = "Delete Role.",
           description = "This endpoint receives roleName for delete.",
           tags = "Manager Users",
           responses = @ApiResponse(responseCode = "204", description = "No Content")
   )
   @DefaultAuthAPIResponses
   @DeleteMapping("/role/{roleName}")
   ResponseEntity<?> deleteRole(
           @PathVariable(name = "roleName") String roleName
   );

   @Operation(
           operationId = "findRoles",
           summary = "Find all roles.",
           description = "This endpoint returns all roles.",
           tags = "Manager Users",
           responses = @ApiResponse(responseCode = "200", description = "OK", content = {@Content(schema = @Schema(implementation = ListRolesInfoResponse.class))})
   )
   @DefaultAuthAPIResponses
   @GetMapping("/role")
   ResponseEntity<ListRolesInfoResponse> findRoles();

   @Operation(
           operationId = "createGroup",
           summary = "Create group.",
           description = "This endpoint receives group data for create.",
           tags = "Manager Users",
           responses = @ApiResponse(responseCode = "201", description = "Created"),
           requestBody = @RequestBody(content = {@Content(examples = @ExampleObject(value = CreateGroupRequest.exampleRequest))})
   )
   @DefaultAuthAPIResponses
   @PostMapping("/group")
   ResponseEntity<?> createGroup(@RequestBody CreateGroupRequest request);

   @Operation(
           operationId = "updateGroup",
           summary = "Update group.",
           description = "This endpoint receives and data for group.",
           tags = "Manager Users",
           responses = @ApiResponse(responseCode = "200", description = "OK"),
           requestBody = @RequestBody(content = {@Content(examples = @ExampleObject(value = CreateGroupRequest.exampleRequest))})
   )
   @DefaultAuthAPIResponses
   @PutMapping("/group/{groupId}")
   ResponseEntity<?> updateGroup(
           @PathVariable(name = "groupId") UUID groupId,
           @RequestBody CreateGroupRequest request
   );

   @Operation(
           operationId = "deleteGroup",
           summary = "Delete Group.",
           description = "This endpoint receives groupId for delete.",
           tags = "Manager Users",
           responses = @ApiResponse(responseCode = "204", description = "No Content")
   )
   @DefaultAuthAPIResponses
   @DeleteMapping("/group/{groupId}")
   ResponseEntity<?> deleteGroup(
           @PathVariable(name = "groupId") UUID groupId
   );

   @Operation(
           operationId = "findGroups",
           summary = "Find all groups.",
           description = "This endpoint returns all groups.",
           tags = "Manager Users",
           responses = @ApiResponse(responseCode = "200", description = "OK", content = {@Content(schema = @Schema(implementation = ListGroupsInfosResponse.class))})
   )
   @DefaultAuthAPIResponses
   @GetMapping("/groups")
   ResponseEntity<ListGroupsInfosResponse> findGroups();

   @Operation(
           operationId = "associateOrDisassociateRoleUser",
           summary = "Associate or disassociate Role a with user.",
           description = "This endpoint receives userId and roles data for associate.",
           tags = "Manager Users",
           responses = @ApiResponse(responseCode = "200", description = "Ok"),
           requestBody = @RequestBody(content = {@Content(examples = @ExampleObject(value = ListRolesRequest.exampleRequest))})
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
           tags = "Manager Users",
           responses = @ApiResponse(responseCode = "200", description = "Ok"),
           requestBody = @RequestBody(content = {@Content(examples = @ExampleObject(value = ListRolesRequest.exampleRequest))})
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
           tags = "Manager Users",
           responses = @ApiResponse(responseCode = "200", description = "Ok")
   )
   @DefaultAuthAPIResponses
   @PostMapping("/associations/group/{groupId}/user/{userId}")
   ResponseEntity<?> associateOrDisassociateUserGroup(
           @PathVariable(name = "groupId") UUID groupId,
           @PathVariable(name = "userId") UUID userId,
           @RequestParam(name = "associate") Boolean associate
   );
}
