package com.markp.importer.uploader;

import io.minio.*;
import io.minio.errors.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;


@Component
public class MinioFileUploader implements FileUploader {

    @Value("${minio.endpoint}")
    private String minioEndpoint;

    @Value("${minio.access-key}")
    private String accessKey;

    @Value("${minio.secret-access-key}")
    private String secretAccessKey;

    @Value("${minio.bucket-name}")
    private String bucketName;

    MinioClient minioClient;

    @PostConstruct
    public void createClient() {
        try {
            minioClient = MinioClient.builder()
                            .endpoint(minioEndpoint)
                            .credentials(accessKey, secretAccessKey)
                            .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks to see if file exists by querying the metadata of the file.
     * @param file
     * @return true if the files metadata exists
     */
    @Override
    public boolean checkFileExists(File file) {
        try {
            // Object metadata
            StatObjectResponse response = minioClient.statObject(StatObjectArgs.builder()
                    .bucket(bucketName)
                    .object(file.getName()).build());
            System.out.println(file.getName() + " found in bucket " + bucketName + " " + response);
            return response.object().equals(file.getName());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void deleteFile(File file) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(file.getName()).build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteBucket() {
        try {
            minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            e.printStackTrace();
        }    }

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
