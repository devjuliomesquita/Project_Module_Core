package com.juliomesquita.core.services.storage;

import com.juliomesquita.core.services.storage.interfaces.GoogleCloudStorageService;
import com.juliomesquita.core.services.storage.interfaces.S3StorageService;

public interface StorageFacade {
   S3StorageService getS3Storage();

   GoogleCloudStorageService getGoogleCloudStorage();
}
