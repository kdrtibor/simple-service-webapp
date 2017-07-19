package com.example;

import com.example.authentication.Credentials;
import com.example.authentication.Token;
import com.example.model.UserStory;
import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.JSONObject;
import org.junit.Test;

import javax.json.JsonObject;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TestTest {


    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testPostMethod() throws IOException,ParseException {


        HttpTransport transport = new NetHttpTransport();
        HttpRequestFactory requestFactory = transport.createRequestFactory();

        GenericUrl url = new GenericUrl("http://localhost:8080/api/userstories");


        UserStory userStory = new UserStory(10, "newus", 10);
        System.out.println(mapper.writeValueAsString(userStory));
        String token = "Bearer " + authenticate();
        HttpRequest request =
                requestFactory.buildPostRequest(url, new ByteArrayContent("application/json", mapper.writeValueAsString(userStory).getBytes()))
                        .setHeaders(new HttpHeaders().set("Authorization", Collections.singletonList(token)));

        String response = request.execute().parseAsString();
        System.out.println(response);

    }

    @Test
    public void testGetMethod() throws IOException,ParseException {


        HttpTransport transport = new NetHttpTransport();
        HttpRequestFactory requestFactory = transport.createRequestFactory();

        GenericUrl url = new GenericUrl("http://localhost:8080/api/userstories");
        String token = "Bearer " + authenticate();
        HttpRequest request =
                requestFactory
                        .buildGetRequest(url)
                        .setHeaders(new HttpHeaders()
                                .set("Authorization", Collections.singletonList(token))
                        );

        Collection<UserStory> userStories = mapper.readValue(request.execute().parseAsString(), new TypeReference<List<UserStory>>() {
        });

        for (UserStory us : userStories) {
            System.out.println(us.toString());
        }

    }

    @Test
    public void testDeleteMethod() throws IOException,ParseException {
        HttpTransport transport = new NetHttpTransport();
        HttpRequestFactory requestFactory = transport.createRequestFactory();

        GenericUrl url = new GenericUrl("http://localhost:8080/api/userstories/0");
        String token = "Bearer " + authenticate();
        HttpRequest request =
                requestFactory
                        .buildDeleteRequest(url)
                        .setHeaders(new HttpHeaders()
                                .set("Authorization", Collections.singletonList(token))
                        );
        System.out.println(request.execute().parseAsString());
    }

    @Test
    public void testPutMethod() throws IOException,ParseException {
        HttpTransport transport = new NetHttpTransport();
        HttpRequestFactory requestFactory = transport.createRequestFactory();

        GenericUrl url = new GenericUrl("http://localhost:8080/api/userstories/10");
        String token = "Bearer " + authenticate();

        UserStory userStory = new UserStory(10, "newus 22", 1011);
        HttpRequest request =
                requestFactory
                        .buildPutRequest(url, new ByteArrayContent("application/json", mapper.writeValueAsString(userStory).getBytes()))
                        .setHeaders(new HttpHeaders()
                                .set("Authorization", Collections.singletonList(token))
                        );
        System.out.println(request.execute().parseAsString());
    }

    @Test
    public void getLoginToken() throws IOException,ParseException {
        String token = authenticate();

        System.out.println(token);
    }


    public String authenticate() throws IOException, ParseException {
        HttpTransport transport = new NetHttpTransport();
        HttpRequestFactory requestFactory = transport.createRequestFactory();
        //should read from file

        Properties prop = new Properties();
        InputStream input = null;

        try {

            input = new FileInputStream("C:\\simple-service-webapp\\simple-service-webapp-tests\\src\\test\\resources\\credentials.properties");
            prop.load(input);
            //set the url
            GenericUrl url = new GenericUrl(prop.getProperty("url"));
            //set the credentials
            Credentials credentials = new Credentials(prop.getProperty("username"),prop.getProperty("password"));

            HttpRequest request =
                    requestFactory
                            .buildPostRequest(url, new ByteArrayContent("application/json", mapper.writeValueAsString(credentials).getBytes()));



            return request.execute().parseAsString();
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


        return "failed to get properties";
    }

}
