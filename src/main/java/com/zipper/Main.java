package com.zipper;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {

        log("Loading config", true);

        ConfigHandler config = new ConfigHandler();

        String input = config.GetSelectedInputPath();
        String output = config.GetSelectedOutputPath();

        boolean debug = config.GetDebug();

        int maxFileSize = config.GetMaxFileSize();

        log("Succesfully loaded config", true);

        log("Obtaining all files in input folder", debug);

        java.io.File file = new java.io.File(input);
        ArrayList<File> files = FileReader.ListFilesForFolder(file);

        log("Succesfully obtained all files in input folder", debug);

        log("Sorting files into groups", debug);

        ArrayList<File> group = new ArrayList<>();
        ArrayList<File> leftover = new ArrayList<>();

        int groupID = 0;

        for (File fileEntry : files) {
            long size = GetGroupSize(group);

            long includedSize = size += fileEntry.GetFileSize();

            log("Processing " + fileEntry.GetFileName(), debug);

            if (includedSize > maxFileSize) {
                groupID++;
                includedSize = 0;

                log("Creating group " + groupID, debug);

                MoveGroup(group, output, groupID);
                group.clear();
                leftover.clear();

                log("Created group " + groupID, debug);

            }
            log("Processed " + fileEntry.GetFileName(), debug);
            group.add(fileEntry);
            leftover.add(fileEntry);

        }
        MoveGroup(leftover, output, groupID + 1);
        log("Finished zipping all files", debug);
    }

    public static long GetGroupSize(ArrayList<File> group) {
        long size = 0;
        for (File fileEntry : group) {
            size += fileEntry.GetFileSize();
        }

        return size;
    }

    public static void MoveGroup(ArrayList<File> group, String path, int groupID) {
        try {
            java.io.File outputFile = new java.io.File(path + "/group-" + groupID + ".zip");

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

    public static void log(String message, boolean debug) {
        if (debug) {
            System.out.println(message);
        }
    }
}