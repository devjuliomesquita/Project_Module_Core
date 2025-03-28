package com.juliomesquita.core.services.storage.implementations;

import com.juliomesquita.core.services.storage.interfaces.S3StorageService;
import com.juliomesquita.core.shared.validations.Notification;
import io.vavr.API;
import io.vavr.control.Either;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.nio.ByteBuffer;
import java.time.Duration;
import java.util.UUID;
import java.util.function.Function;


public class S3StorageServiceImpl implements S3StorageService {
   private final S3Client client;
   private final S3Presigner presigner;
   private final String bucketName;

   public S3StorageServiceImpl(
           final String region,
           final String bucketName,
           final String accessKey,
           final String secretKey
   ) {
      this.bucketName = bucketName;
      this.client = S3Client.builder()
              .region(Region.of(region))
              .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
              .build();
      this.presigner = S3Presigner.builder()
              .region(Region.of(region))
              .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
              .build();
   }

   @Override
   public Either<Notification, String> upload(final MultipartFile file, final String folder, final String hash) {
      final String generatedHash = hash != null ? hash : UUID.randomUUID().toString();
      final String fileId = String.format("%s/%s::::%s", folder, generatedHash, file.getOriginalFilename());
      final PutObjectRequest request = PutObjectRequest.builder()
              .bucket(this.bucketName)
              .key(fileId)
              .contentType(file.getContentType())
              .build();
      return API.Try(() -> {
                         final ByteBuffer buffer = ByteBuffer.wrap(file.getBytes());
                         this.client.putObject(request, RequestBody.fromByteBuffer(buffer));
                         return fileId;
                      }
              )
              .toEither()
              .bimap(Notification::create, Function.identity());
   }

   @Override
   public Either<Notification, Resource> download(final String fileId) {
      final GetObjectRequest request = GetObjectRequest.builder()
              .bucket(this.bucketName)
              .key(fileId)
              .build();
      return API.Try(() -> {
                 final ResponseBytes<GetObjectResponse> objectAsBytes = this.client.getObjectAsBytes(request);
                 return new ByteArrayResource(objectAsBytes.asByteArray());
              })
              .toEither()
              .bimap(Notification::create, Function.identity());
   }

   @Override
   public Either<Notification, Boolean> delete(String fileId) {
      final DeleteObjectRequest request = DeleteObjectRequest.builder()
              .bucket(this.bucketName)
              .key(fileId)
              .build();
      return API.Try(() -> {
                 DeleteObjectResponse response = this.client.deleteObject(request);
                 return response.sdkHttpResponse().isSuccessful();
              })
              .toEither()
              .bimap(Notification::create, Function.identity());
   }

   @Override
   public Either<Notification, String> getUrlSigned(String fileId, Integer expiresIn) {
      final GetObjectPresignRequest request = GetObjectPresignRequest.builder()
              .signatureDuration(Duration.ofSeconds(expiresIn))
              .getObjectRequest(o -> o.bucket(this.bucketName).key(fileId))
              .build();
      return API.Try(() -> {
                 PresignedGetObjectRequest response = this.presigner.presignGetObject(request);
                 return response.url().toString();
              })
              .toEither()
              .bimap(Notification::create, Function.identity());
   }
}
