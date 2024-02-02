package com.markp.importer.uploader;

import com.markp.importer.deleter.FileDeleter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class FileUploaderTests {

    @Autowired
    private FileUploader fileUploader;

    @Autowired
    private FileDeleter fileDeleter;

    public void cleanup(String filename) {
        fileDeleter.deleteFile(filename);
        fileDeleter.deleteBucket();
    }

    @Test
    public void shouldUploadFile() {
        // given file is available to be uploaded
        String filename = "MINIO_Bird.png";
        String filepath = "src\\test\\resources\\" + filename;

        // when uploading the file
        fileUploader.uploadFile(filename, filepath);

        // then the file should be uploaded
        boolean fileUploaded = fileDeleter.checkFileExists(filename);
        assertTrue(fileUploaded);

        // cleanup
        cleanup(filename);
    }
}
