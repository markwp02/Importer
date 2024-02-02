package com.markp.importer.uploader;

import com.markp.importer.client.MinioImporterClient;
import io.minio.*;
import io.minio.errors.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;


@Component
public class MinioFileUploader implements FileUploader {

    @Value("${minio.bucket-name}")
    private String bucketName;

    @Autowired
    private MinioImporterClient minioImporterClient;

    private MinioClient minioClient;

    @PostConstruct
    public void setClient() {
        minioClient = minioImporterClient.getMinioClient();
    }

    @Override
    public void uploadFile(File file) {

        try {
            createBucketIfNotExists(bucketName);

            minioClient.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket(bucketName)
                            .object(file.getName())
                            .filename(file.getPath())
                            .build());
            System.out.println(file.getName() + " has successfully been uploaded to bucket " + bucketName);

        } catch (MinioException minioException) {
            System.out.println("Error occurred: " + minioException);
            System.out.println("HTTP trace: " + minioException.httpTrace());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createBucketIfNotExists(String bucketName) throws Exception {
        boolean found =
                minioClient.bucketExists(BucketExistsArgs.builder()
                        .bucket(bucketName).build());
        if (!found) {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(bucketName).build());
        } else {
            System.out.println("Bucket " + bucketName + " already exists.");
        }
    }
}
