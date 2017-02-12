package com.chadrc.services.api;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by chad on 2/11/17.
 */
public class ApiProperties {
    private static Properties properties = new Properties();
    private static boolean loaded = false;
    public ApiProperties() {}

    private static void loadPropertiesFromClassPath() {
        if (loaded) return;
        InputStream input = null;
        try {
            String filename = "request-mapping-processor.properties";
            input = ApiProperties.class.getClassLoader().getResourceAsStream(filename);
            if(input==null){
                System.out.println("Sorry, unable to find " + filename);
            }
            properties.load(input);
            loaded = true;
        } catch (IOException ex) {
            // Couldn't load properties file
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String getRootUrl() {
        loadPropertiesFromClassPath();
        return properties.getProperty("rootUrl");
    }
}
