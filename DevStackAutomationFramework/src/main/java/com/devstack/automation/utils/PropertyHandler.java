package com.devstack.automation.utils;

import java.io.InputStream;
import java.util.Properties;

public class PropertyHandler {
    private static Properties properties = new Properties();

    static {
        try(InputStream input = PropertyHandler.class.getClassLoader().getResourceAsStream("config.properties")){
            if(input==null){
                System.out.println("config.properties file is not found");
            }
            properties.load(input);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
