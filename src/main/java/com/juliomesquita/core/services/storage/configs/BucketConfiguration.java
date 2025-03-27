package com.juliomesquita.core.services.storage.configs;

import com.juliomesquita.core.services.storage.services.GoogleCloudStorageServiceImpl;
import com.juliomesquita.core.services.storage.services.S3StorageService;
import com.juliomesquita.core.services.storage.services.S3StorageServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BucketConfiguration {
   @Value("${configs.bucket.region}")
   private String region;

   @Value("${configs.bucket.name}")
   private String bucketName;

   @Value("${configs.bucket.access-key}")
   private String accessKey;

   @Value("${configs.bucket.secret-key}")
   private String secretKey;

   @Bean
   public S3StorageService s3StorageService() {
      return new S3StorageServiceImpl(region, bucketName, accessKey, secretKey);
   }

   @Bean
   public GoogleCloudStorageServiceImpl googleCloudStorageService(){
      return new GoogleCloudStorageServiceImpl(bucketName);
   }
}
