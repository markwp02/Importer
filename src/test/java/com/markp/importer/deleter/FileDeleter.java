package com.markp.importer.deleter;

import java.io.File;

public interface FileDeleter {

    boolean checkFileExists(File file);

    void deleteFile(File file);

    void deleteBucket();
}
