package com.markp.importer.uploader;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class FileUploaderTests {

    @Autowired
    private FileUploader fileUploader;

    @Test
    public void shouldUploadFile() {
        // given file is available to be uploaded
        String filename = "MINIO_Bird.png";
        File file = new File("src\\test\\resources\\" + filename);

        // when uploading the file
        fileUploader.uploadFile(file);

        // then the file should be uploaded
        boolean fileUploaded = fileUploader.checkFileExists(file);
        assertTrue(fileUploaded);

        // cleanup
        fileUploader.deleteFile(file);
        fileUploader.deleteBucket();
    }
}
