package com.example;


import com.example.rest.authentication.Credentials;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConnectionUtils {

    private String path;

    public ConnectionUtils() {
        this.path = "src/test/resources/connection.properties";
    }

    public Credentials getCredentials(){
        return new Credentials(getProperties().getProperty("username"),getProperties().getProperty("password"));
    }

    public String getUrl(){
        return getProperties().getProperty("url");
    }

    private Properties getProperties(){
        Properties prop = new Properties();
        InputStream input = null;

        try {

            input = new FileInputStream(path);
            prop.load(input);
            return prop;
        }
        catch(IOException e){
            e.printStackTrace();
        }
        finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }
}
