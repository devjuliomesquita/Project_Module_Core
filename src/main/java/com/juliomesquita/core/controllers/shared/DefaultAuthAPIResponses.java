package com.juliomesquita.core.controllers.shared;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@ApiResponses({
        @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = RestAPIErrorResponse.class))}),
        @ApiResponse(responseCode = "401", description = "Unauthorized", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = RestAPIErrorResponse.class))}),
        @ApiResponse(responseCode = "403", description = "Forbidden", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = RestAPIErrorResponse.class))}),
        @ApiResponse(responseCode = "404", description = "Not Found", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = RestAPIErrorResponse.class))}),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = RestAPIErrorResponse.class))}),
        @ApiResponse(responseCode = "502", description = "Bad Gateway", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = RestAPIErrorResponse.class))})
})
public @interface DefaultAuthAPIResponses {
}

