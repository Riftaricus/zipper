package com.zipper;

public class File {
    private String fileName;
    private int fileSize;
    private String filePath;

    public File(String fileName, String filePath, int fileSize) {
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.filePath = filePath;
    }

    public void SetFileName(String name) {
        this.fileName = name;
    }

    public String GetFileName() {
        return this.fileName;
    }

    public void SetFileSize(int size) {
        this.fileSize = size;
    }

    public int GetFileSize() {
        return this.fileSize;
    }

    public void SetFilePath(String path) {
        this.filePath = path;
    }

    public String GetFilePath() {
        return this.filePath;
    }

    @Override
    public String toString() {
        return "FILE - " +  this.fileName + " - " + this.filePath + " - " + this.fileSize;
    }

}
