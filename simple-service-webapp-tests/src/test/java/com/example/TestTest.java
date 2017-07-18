package com.example;

import com.example.authentication.Credentials;
import com.example.model.UserStory;
import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.Json;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import org.apache.http.client.methods.HttpOptions;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.JSONObject;
import org.junit.Test;


import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class TestTest {

    private static final JsonFactory JSON_FACTORY = new JacksonFactory();
    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testPostMethod() throws IOException {


        HttpTransport transport = new NetHttpTransport();
        HttpRequestFactory requestFactory = transport.createRequestFactory();

        GenericUrl url = new GenericUrl("http://localhost:8080/api/userstories");


        UserStory userStory = new UserStory(10, "newus", 10);
        System.out.println(mapper.writeValueAsString(userStory));
        String token = "Bearer "+authenticate();
        HttpRequest request =
                requestFactory.buildPostRequest(url, new ByteArrayContent("application/json", mapper.writeValueAsString(userStory).getBytes()))
                        .setHeaders(new HttpHeaders().set("Authorization", Collections.singletonList(token)));

        String response = request.execute().parseAsString();
        //System.out.println(response);

    }

    @Test
    public void testGetMethod() throws IOException {


        HttpTransport transport = new NetHttpTransport();
        HttpRequestFactory requestFactory = transport.createRequestFactory();

        GenericUrl url = new GenericUrl("http://localhost:8080/api/userstories");
        String token = "Bearer "+authenticate();
        HttpRequest request =
                requestFactory
                        .buildGetRequest(url)
                        .setHeaders(new HttpHeaders()
                                .set("Authorization", Collections.singletonList(token))
                        );

        Collection<UserStory> userStories = mapper.readValue(request.execute().parseAsString(), new TypeReference<List<UserStory>>(){});

        for(UserStory us : userStories){
            System.out.println(us.toString());
        }

    }

    @Test
    public void testDeleteMethod() throws IOException {
        HttpTransport transport = new NetHttpTransport();
        HttpRequestFactory requestFactory = transport.createRequestFactory();

        GenericUrl url = new GenericUrl("http://localhost:8080/api/userstories/10");
        String token = "Bearer "+authenticate();
        HttpRequest request =
                requestFactory
                        .buildDeleteRequest(url)
                        .setHeaders(new HttpHeaders()
                                .set("Authorization", Collections.singletonList(token))
                        );
        System.out.println(request.execute().parseAsString());
    }

    @Test
    public void testPutMethod() throws IOException {
        HttpTransport transport = new NetHttpTransport();
        HttpRequestFactory requestFactory = transport.createRequestFactory();

        GenericUrl url = new GenericUrl("http://localhost:8080/api/userstories/10");
        String token = "Bearer "+authenticate();

        UserStory userStory = new UserStory(10, "newus 22", 1011);
        HttpRequest request =
                requestFactory
                        .buildPutRequest(url,new ByteArrayContent("application/json", mapper.writeValueAsString(userStory).getBytes()))
                        .setHeaders(new HttpHeaders()
                                .set("Authorization", Collections.singletonList(token))
                        );
        System.out.println(request.execute().parseAsString());
    }

    @Test
    public void getLoginToken() throws IOException{
        String token = authenticate();
    }


    public String authenticate() throws IOException{
        HttpTransport transport = new NetHttpTransport();
        HttpRequestFactory requestFactory = transport.createRequestFactory();

        GenericUrl url = new GenericUrl("http://localhost:8080/api/authentication");
        Credentials credentials = new Credentials("abcde","12345");

        HttpRequest request =
                requestFactory
                        .buildPostRequest(url, new ByteArrayContent("application/json", mapper.writeValueAsString(credentials).getBytes()));

        String response = request.execute().parseAsString();
        return response;

    }
}
