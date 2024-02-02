package com.markp.importer.service;

import com.markp.importer.dao.ProductRepository;
import com.markp.importer.entity.Product;
import com.markp.importer.model.ProductMessage;
import com.markp.importer.uploader.FileUploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class ImporterProductService implements ImporterService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    FileUploader fileUploader;

    @Value("${minio.console}")
    private String minioConsole;

    @Value("${minio.bucket-name}")
    private String bucketName;

    @Override
    public void processMessage(ProductMessage productMessage) {

        // Store image in object store
        fileUploader.uploadFile(productMessage.getProductImageFilename(), productMessage.getProductImageFilepath());

        // Insert product in database

        String productImageUrl = minioConsole + bucketName + File.separator + productMessage.getProductImageFilename();

        Product product = Product.builder()
                .productId(0)
                .productName(productMessage.getProductName())
                .productCategory(productMessage.getProductCategory())
                .productDescription(productMessage.getProductDescription())
                .productPrice(productMessage.getProductPrice())
                .productStock(productMessage.getProductStock())
                .productImageUrl(productImageUrl)
                .build();

        productRepository.save(product);
    }
}
