package com.markp.importer.deleter;

import com.markp.importer.client.MinioImporterClient;
import io.minio.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MinioFileDeleter implements FileDeleter {

    @Value("${minio.bucket-name}")
    private String bucketName;

    @Autowired
    private MinioImporterClient minioImporterClient;

    private MinioClient minioClient;

    @PostConstruct
    public void setClient() {
        minioClient = minioImporterClient.getMinioClient();
    }

    /**
     * Checks to see if file exists by querying the metadata of the file.
     * @param filename
     * @return true if the files metadata exists
     */
    @Override
    public boolean checkFileExists(String filename) {
        try {
            // Object metadata
            StatObjectResponse response = minioClient.statObject(StatObjectArgs.builder()
                    .bucket(bucketName)
                    .object(filename).build());
            System.out.println(filename + " found in bucket " + bucketName + " " + response);
            return response.object().equals(filename);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void deleteFile(String filename) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(filename).build());
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

}
