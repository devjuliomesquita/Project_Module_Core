package com.juliomesquita.core.controllers.manageusers.api;

import com.juliomesquita.core.controllers.manageusers.dtos.*;
import com.juliomesquita.core.controllers.shared.DefaultPublicAPIResponses;
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


@Tag(name = "Manager Token", description = "API de gerenciamento de token.")
public interface ManagerTokenFlow {
   @Operation(
           operationId = "loginUser",
           summary = "Login user.",
           description = "This endpoint receives username and password for login.",
           tags = {"Manager Token"},
           responses = @ApiResponse(responseCode = "200", description = "OK", content = {@Content(schema = @Schema(implementation = UserLoginResponse.class))}),
           requestBody = @RequestBody(content = {@Content(examples = @ExampleObject(value = LoginUserRequest.exampleRequest))})
   )
   @DefaultPublicAPIResponses
   @PostMapping("/auth/login")
   ResponseEntity<?> loginUser(@RequestBody LoginUserRequest request);

   @Operation(
           operationId = "refreshToken",
           summary = "Refresh token user.",
           description = "This endpoint receives refresh token and returns new login token.",
           tags = {"Manager Token"},
           responses = @ApiResponse(responseCode = "200", description = "OK", content = {@Content(schema = @Schema(implementation = UserLoginResponse.class))})
   )
   @DefaultPublicAPIResponses
   @PostMapping("/auth/refresh-token")
   ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest request);

   @Operation(
           operationId = "logoutUser",
           summary = "Logout user.",
           description = "This endpoint receives refresh token for logout.",
           tags = {"Manager Token"},
           responses = @ApiResponse(responseCode = "204", description = "No Content")
   )
   @DefaultPublicAPIResponses
   @PostMapping("/auth/logout")
   ResponseEntity<?> logoutUser(@RequestBody RefreshTokenRequest request);
}
