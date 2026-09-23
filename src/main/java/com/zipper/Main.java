package com.zipper;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {

        System.out.println("Loading config");

        ConfigHandler config = new ConfigHandler();

        String input = config.GetSelectedInputPath();
        String output = config.GetSelectedOutputPath();

        int maxFileSize = config.GetMaxFileSize();

        System.out.println("Succesfully loaded config");

        System.out.println("Obtaining all files in input folder");

        java.io.File file = new java.io.File(input);
        ArrayList<File> files = FileReader.ListFilesForFolder(file);

        System.out.println("Succesfully obtained all files in input folder");

        System.out.println("Sorting files into groups");

        ArrayList<File> group = new ArrayList<>();

        int groupID = 0;

        for (File fileEntry : files) {
            long size = GetGroupSize(group);

            long includedSize = size += fileEntry.GetFileSize();

            System.out.println("Processing " + fileEntry.GetFileName());

            if (includedSize > maxFileSize) {
                groupID++;
                includedSize = 0;

                System.out.println("Creating group " + groupID);

                MoveGroup(group, output);
                group.clear();

                System.out.println("Created group " + groupID);

            }
            System.out.println("Processed " + fileEntry.GetFileName());
            group.add(fileEntry);

        }
        System.out.println("Finished zpping all files");
    }

    public static long GetGroupSize(ArrayList<File> group) {
        long size = 0;
        for (File fileEntry : group) {
            size += fileEntry.GetFileSize();
        }

        return size;
    }

    public static void MoveGroup(ArrayList<File> group, String path) {
        try {
            java.io.File outputFile = new java.io.File(path + ".zip");

            // Make sure the parent directory exists
            java.io.File parent = outputFile.getParentFile();
            if (parent != null) {
                parent.mkdirs();
            }

            try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(outputFile))) {

                for (File fileEntry : group) {

                    java.io.File inputFile = fileEntry.GetFile();

                    ZipEntry zipEntry = new ZipEntry(fileEntry.GetRelativePath());
                    zipOut.putNextEntry(zipEntry);

                    try (FileInputStream inputStream = new FileInputStream(inputFile)) {

                        byte[] buffer = new byte[8192];
                        int length;

                        while ((length = inputStream.read(buffer)) > 0) {
                            zipOut.write(buffer, 0, length);
                        }
                    }

                    zipOut.closeEntry();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}