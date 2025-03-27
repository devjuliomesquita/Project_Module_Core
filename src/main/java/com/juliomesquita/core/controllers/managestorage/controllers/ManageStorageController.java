package com.juliomesquita.core.controllers.managestorage.controllers;

import com.juliomesquita.core.controllers.managestorage.api.ManagerStorageFlow;
import com.juliomesquita.core.controllers.managestorage.dtos.FileIdResponse;
import com.juliomesquita.core.controllers.managestorage.dtos.UrlSignedResponse;
import com.juliomesquita.core.services.storage.StorageFacade;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
/*
* Você pode utilizar nos métodos "this.storageFacade.getGoogleCloudStorage()" e implementar via googleCloud
* */
@RestController
public class ManageStorageController implements ManagerStorageFlow {
   private static final Integer EXPIRES_IN = 600;

   private final StorageFacade storageFacade;

   public ManageStorageController(final StorageFacade storageFacade) {
      this.storageFacade = Objects.requireNonNull(storageFacade);
   }

   @Override
   public ResponseEntity<?> uploadFile(final MultipartFile file, final String folder) {
      final Function<String, ResponseEntity<?>> onSuccess =
              id -> ResponseEntity.ok(FileIdResponse.with(id));

      return this.storageFacade.getS3Storage()
              .upload(file, folder, UUID.randomUUID().toString())
              .fold(
                      ResponseEntity.badRequest()::body, onSuccess
              );
   }

   @Override
   public ResponseEntity<?> downloadFile(String fileId) {
      final Function<Resource, ResponseEntity<?>> onSuccess =
              resource -> ResponseEntity.ok()
                      .contentType(MediaType.APPLICATION_OCTET_STREAM)
                      .header(
                              HttpHeaders.CONTENT_DISPOSITION,
                              "attachment; filename=\"" + fileId.substring(fileId.lastIndexOf("/") + 1) + "\""
                      )
                      .body(resource);

      return this.storageFacade.getS3Storage()
              .download(fileId)
              .fold(
                      ResponseEntity.badRequest()::body, onSuccess
              );
   }

   @Override
   public ResponseEntity<?> urlSignedFile(String fileId) {
      final Function<String, ResponseEntity<?>> onSuccess =
              url -> ResponseEntity.ok(UrlSignedResponse.with(url));

      return this.storageFacade.getS3Storage()
              .getUrlSigned(fileId, EXPIRES_IN)
              .fold(
                      ResponseEntity.badRequest()::body, onSuccess
              );
   }

   @Override
   public ResponseEntity<?> deleteFile(String fileId) {
      return this.storageFacade.getS3Storage()
              .delete(fileId)
              .fold(
                      ResponseEntity.badRequest()::body,
                      b -> ResponseEntity.noContent().build()
              );
   }
}
