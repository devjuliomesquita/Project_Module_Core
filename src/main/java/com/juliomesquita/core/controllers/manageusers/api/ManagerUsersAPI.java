package com.juliomesquita.core.controllers.manageusers.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.juliomesquita.core.controllers.manageusers.dtos.CreateUserRequest;
import com.juliomesquita.core.controllers.shared.DefaultPublicAPIResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
