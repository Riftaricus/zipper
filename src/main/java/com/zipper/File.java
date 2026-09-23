package com.zipper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class File {
    private String fileName;
    private long fileSize;
    private String filePath;
    private String relativePath;
    private java.io.File file;

    public File(String fileName, String filePath, long fileSize, String relativePath) {
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.filePath = filePath;
        this.relativePath = relativePath;
        this.file = new java.io.File(filePath);
    }

    public String GetFileName() {
        return this.fileName;
    }

    public long GetFileSize() {
        return this.fileSize;
    }

    public String GetFilePath() {
        return this.filePath;
    }

    public String GetRelativePath() {
        return this.relativePath;
    }
    
    public java.io.File GetFile() {
        return this.file;
    }

    public boolean Move(String newPath) {
        Path source = java.nio.file.Paths.get(this.filePath);
        Path destination = java.nio.file.Paths.get(newPath, this.relativePath);

        try {
            Files.createDirectories(destination.getParent());
            Files.move(
                    source,
                    destination,
                    StandardCopyOption.REPLACE_EXISTING);

            this.filePath = destination.toString();
            this.file = new java.io.File(this.filePath);
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String toString() {
        return "FILE - " + this.fileName + " - " + this.filePath + " - " + this.fileSize + " - " + this.relativePath;
    }

}
