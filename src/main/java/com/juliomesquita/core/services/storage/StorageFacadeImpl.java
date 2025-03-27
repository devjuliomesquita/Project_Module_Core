package com.juliomesquita.core.services.storage;

import com.juliomesquita.core.services.storage.interfaces.GoogleCloudStorageService;
import com.juliomesquita.core.services.storage.interfaces.S3StorageService;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class StorageFacadeImpl implements StorageFacade {
   private final S3StorageService s3StorageService;
   private final GoogleCloudStorageService googleCloudStorageService;

   public StorageFacadeImpl(
           final S3StorageService s3StorageService,
           final GoogleCloudStorageService googleCloudStorageService
   ) {
      this.s3StorageService = Objects.requireNonNull(s3StorageService);
      this.googleCloudStorageService = Objects.requireNonNull(googleCloudStorageService);
   }

   @Override
   public S3StorageService getS3Storage() {
      return this.s3StorageService;
   }

   @Override
   public GoogleCloudStorageService getGoogleCloudStorage() {
      return this.googleCloudStorageService;
   }
}
