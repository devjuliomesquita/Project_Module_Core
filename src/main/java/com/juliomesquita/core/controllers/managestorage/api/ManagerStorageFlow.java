package com.juliomesquita.core.controllers.managestorage.api;

import com.juliomesquita.core.controllers.managestorage.dtos.FileIdResponse;
import com.juliomesquita.core.controllers.managestorage.dtos.UrlSignedResponse;
import com.juliomesquita.core.controllers.manageusers.dtos.ListRolesInfoResponse;
import com.juliomesquita.core.controllers.shared.DefaultAuthAPIResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Manager Storage", description = "API de gerenciamento de arquivos.")
@RequestMapping(value = "/file")
public interface ManagerStorageFlow {
   @Operation(
           operationId = "uploadFile",
           summary = "Upload File.",
           description = "This endpoint receives a file for upload.",
           tags = {"Manager Storage"},
           responses = @ApiResponse(responseCode = "200", description = "Ok", content = {@Content(schema = @Schema(implementation = FileIdResponse.class))}),
           security = @SecurityRequirement(name = "bearerAuth")
   )
   @DefaultAuthAPIResponses
   @PostMapping("/upload")
   ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("folder") String folder);

   @Operation(
           operationId = "downloadFile",
           summary = "Download File.",
           description = "This endpoint receives a fileId for download.",
           tags = {"Manager Storage"},
           responses = @ApiResponse(responseCode = "200", description = "Ok"),
           security = @SecurityRequirement(name = "bearerAuth")
   )
   @DefaultAuthAPIResponses
   @GetMapping("/download")
   ResponseEntity<?> downloadFile(@RequestParam("fileId") String fileId);

   @Operation(
           operationId = "urlSignedFile",
           summary = "URL Signed File.",
           description = "This endpoint receives a fileId for url signed.",
           tags = {"Manager Storage"},
           responses = @ApiResponse(responseCode = "200", description = "Ok", content = {@Content(schema = @Schema(implementation = UrlSignedResponse.class))}),
           security = @SecurityRequirement(name = "bearerAuth")
   )
   @DefaultAuthAPIResponses
   @GetMapping()
   ResponseEntity<?> urlSignedFile(@RequestParam("fileId") String fileId);

   @Operation(
           operationId = "deleteFile",
           summary = "Delete File.",
           description = "This endpoint receives a fileId for delete file.",
           tags = {"Manager Storage"},
           responses = @ApiResponse(responseCode = "204", description = "No content"),
           security = @SecurityRequirement(name = "bearerAuth")
   )
   @DefaultAuthAPIResponses
   @DeleteMapping()
   ResponseEntity<?> deleteFile(@RequestParam("fileId") String fileId);
}
