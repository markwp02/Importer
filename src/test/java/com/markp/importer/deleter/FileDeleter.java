package com.markp.importer.deleter;

public interface FileDeleter {

    boolean checkFileExists(String filename);

    void deleteFile(String filename);

    void deleteBucket();
}
