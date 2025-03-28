package com.juliomesquita.core.services.storage.implementations;

import com.google.cloud.storage.*;
import com.juliomesquita.core.services.storage.interfaces.GoogleCloudStorageService;
import com.juliomesquita.core.shared.validations.Notification;
import io.vavr.API;
import io.vavr.control.Either;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class GoogleCloudStorageServiceImpl implements GoogleCloudStorageService {
   private final Storage storage;
   private final String bucketName;

   public GoogleCloudStorageServiceImpl(final String bucketName) {
      this.bucketName = Objects.requireNonNull(bucketName);
      this.storage = StorageOptions.getDefaultInstance().getService();
   }

   public Either<Notification, String> upload(final MultipartFile file, final String folder, final String hash) {
      final String generatedHash = hash != null ? hash : UUID.randomUUID().toString();
      final String fileId = String.format("%s/%s::::%s", folder, generatedHash, file.getOriginalFilename());
      final BlobId blobId = BlobId.of(this.bucketName, fileId);
      final BlobInfo blobInfo = BlobInfo
              .newBuilder(blobId)
              .setContentType(file.getContentType())
              .build();
      return API.Try(() -> {
                 this.storage.create(blobInfo, file.getBytes());
                 return String.format("https://storage.googleapis.com/%s/%s", bucketName, file.getOriginalFilename());
              })
              .toEither()
              .bimap(Notification::create, Function.identity());
   }

   public Either<Notification, Resource> download(final String fileId) {
      final BlobId blobId = BlobId.of(this.bucketName, fileId);
      return API.Try(() -> {
                 final Blob blob = this.storage.get(blobId);
                 if (blob == null) {
                    return null;
                 }
                 return new ByteArrayResource(blob.getContent());
              })
              .toEither()
              .bimap(Notification::create, Function.identity());
   }

   public Either<Notification, Boolean> delete(final String fileId) {
      final BlobId blobId = BlobId.of(this.bucketName, fileId);
      return API.Try(() -> this.storage.delete(blobId))
              .toEither()
              .bimap(Notification::create, Function.identity());
   }

   public Either<Notification, String> getUrlSigned(final String fileId, final Integer expiresIn) {
      final BlobId blobId = BlobId.of(this.bucketName, fileId);
      final BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();

      return API.Try(() -> {
                 final URL url = this.storage
                         .signUrl(blobInfo, expiresIn, TimeUnit.SECONDS, Storage.SignUrlOption.withV4Signature());
                 return url.toString();
              })
              .toEither()
              .bimap(Notification::create, Function.identity());

   }
}
