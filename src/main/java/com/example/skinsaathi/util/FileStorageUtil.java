package com.example.skinsaathi.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class FileStorageUtil {

    private static final String TEMP_DIR = System.getProperty("java.io.tmpdir");

    public static String storeTempFile(MultipartFile file) {

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        File tempFile = new File(TEMP_DIR, fileName);

        try {
            file.transferTo(tempFile);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file");
        }

        return tempFile.getAbsolutePath();
    }

    public static void deleteTempFile(String path) {
        if (path != null) {
            new File(path).delete();
        }
    }
}
