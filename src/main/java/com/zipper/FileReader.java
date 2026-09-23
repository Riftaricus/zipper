package com.zipper;

import java.io.File;
import java.util.ArrayList;

public class FileReader {
    public static ArrayList<com.zipper.File> ListFilesForFolder(File folder, String relativePath) {
        ArrayList<com.zipper.File> files = new ArrayList<>();

        try {

            for (File fileEntry : folder.listFiles()) {
                if (fileEntry.isDirectory()) {
                    files.addAll(ListFilesForFolder(fileEntry, relativePath + "/" + fileEntry.getName()));
                } else {
                    com.zipper.File file = new com.zipper.File(fileEntry.getName(), fileEntry.getAbsolutePath(),
                            fileEntry.length(), relativePath + "/" + fileEntry.getName());

                    files.add(file);
                }
            }
        } catch (NullPointerException e) {
            System.out.println("An error has occured with obtaining files");
            e.printStackTrace();
        }

        return files;
    }

    public static ArrayList<com.zipper.File> ListFilesForFolder(File folder) {
        ArrayList<com.zipper.File> files = new ArrayList<>();

        try {

            for (File fileEntry : folder.listFiles()) {
                if (fileEntry.isDirectory()) {
                    files.addAll(ListFilesForFolder(fileEntry, fileEntry.getName()));
                } else {
                    com.zipper.File file = new com.zipper.File(fileEntry.getName(), fileEntry.getAbsolutePath(),
                            fileEntry.length(), fileEntry.getName());

                    files.add(file);
                }
            }
        } catch (NullPointerException e) {
            System.out.println("An error has occured with obtaining files");
            e.printStackTrace();
        }

        return files;
    }
}
