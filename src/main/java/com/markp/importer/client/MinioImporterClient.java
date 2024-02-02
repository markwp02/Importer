package com.markp.importer.client;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.minio.MinioClient;

@Component
public class MinioImporterClient {

    @Value("${minio.endpoint}")
    private String minioEndpoint;

    @Value("${minio.access-key}")
    private String accessKey;

    @Value("${minio.secret-access-key}")
    private String secretAccessKey;

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

    public MinioClient getMinioClient() {
        return minioClient;
    }
}
