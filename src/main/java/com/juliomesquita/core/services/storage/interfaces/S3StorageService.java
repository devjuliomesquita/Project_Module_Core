package com.juliomesquita.core.services.storage.interfaces;

import com.juliomesquita.core.shared.validations.Notification;
import io.vavr.control.Either;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface S3StorageService {
   Either<Notification, String> upload(MultipartFile file, String folder, String hash);

   Either<Notification, Resource> download(String fileId);

   Either<Notification, Boolean> delete(String fileId);

   Either<Notification, String> getUrlSigned(String fileId, Integer expiresIn);
}
