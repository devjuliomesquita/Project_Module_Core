package com.juliomesquita.core.services.storage.services;

import com.juliomesquita.core.services.storage.dtos.FileBucket;
import com.juliomesquita.core.shared.validations.Notification;
import io.vavr.control.Either;

public interface S3StorageService {
   Either<Notification, String> upload(FileBucket file, String folder, String hash);

   Either<Notification, Void> delete(String fileId);

   Either<Notification, String> getUrlSigned(String fileId, Integer expiresIn);
}
