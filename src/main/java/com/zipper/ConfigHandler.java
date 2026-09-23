package com.zipper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigHandler {

    private Properties properties;

    private String selectedInputPath;

    private String selectedOutputPath;

    private int maxFileSize;

    public ConfigHandler() {
        this.properties = GetProperties();

        this.selectedInputPath = GetStringProperty("input.path");

        this.selectedOutputPath = GetStringProperty("output.path");

        this.maxFileSize = Integer.parseInt(GetStringProperty("zipper.max_file_size"));
    }

    public String GetStringProperty(String key) {
        return properties.getProperty(key);
    }

    private Properties GetProperties() {
        Properties properties = new Properties();

        try (InputStream input = ConfigHandler.class
                .getClassLoader()
                .getResourceAsStream("config.properties")) {

            if (input == null) {
                throw new FileNotFoundException("config.properties not found");
            }

            properties.load(input);
        } catch (IOException e) {
            System.out.println("An error has occured with the config");
            e.printStackTrace();
        }

        return properties;
    }

    public String GetSelectedInputPath() {
        return this.selectedInputPath;
    }

    public String GetSelectedOutputPath() {
        return this.selectedOutputPath;
    }

    public int GetMaxFileSize() {
        return this.maxFileSize;
    }
}
