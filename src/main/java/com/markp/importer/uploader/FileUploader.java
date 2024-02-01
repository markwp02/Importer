package com.markp.importer.uploader;

import java.io.File;

public interface FileUploader {

    void uploadFile(File file);

    boolean checkFileExists(File file);

    void deleteFile(File file);

    void deleteBucket();
}
