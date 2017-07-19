package com.example;

import com.example.model.UserStory;
import com.example.rest.authentication.Credentials;
import com.example.rest.authentication.Token;
import com.example.rest.config.CustomJsonProvider;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class TestTest {

    private static ObjectMapper mapper = new ObjectMapper();
    private ConnectionUtils connectionUtils = new ConnectionUtils();

    static {
        SimpleModule module = new SimpleModule();
        module.addSerializer(LocalDateTime.class, new CustomJsonProvider.CustomLocalDateTimeSerializer());
        module.addDeserializer(LocalDateTime.class, new CustomJsonProvider.CustomLocalDateTimeDeserializer());
        mapper.registerModule(module);
    }

    @Test
    public void testPostMethod() throws IOException, ParseException {


        HttpTransport transport = new NetHttpTransport();
        HttpRequestFactory requestFactory = transport.createRequestFactory();

        GenericUrl url = new GenericUrl(connectionUtils.getUrl() + "userstories");


        UserStory userStory = new UserStory(10, "newus", 10);
        System.out.println(mapper.writeValueAsString(userStory));
        String token = "Bearer " + authenticate().getKey();
        HttpRequest request =
                requestFactory.buildPostRequest(url, new ByteArrayContent("application/json", mapper.writeValueAsString(userStory).getBytes()))
                        .setHeaders(new HttpHeaders().set("Authorization", Collections.singletonList(token)));

        String response = request.execute().parseAsString();
        System.out.println(response);

    }

    @Test
    public void testGetMethod() throws IOException, ParseException {


        HttpTransport transport = new NetHttpTransport();
        HttpRequestFactory requestFactory = transport.createRequestFactory();

        GenericUrl url = new GenericUrl(connectionUtils.getUrl() + "userstories");
        String token = "Bearer " + authenticate().getKey();
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
    public void testDeleteMethod() throws IOException, ParseException {
        HttpTransport transport = new NetHttpTransport();
        HttpRequestFactory requestFactory = transport.createRequestFactory();

        GenericUrl url = new GenericUrl(connectionUtils.getUrl() + "userstories");
        String token = "Bearer " + authenticate().getKey();
        HttpRequest request =
                requestFactory
                        .buildDeleteRequest(url)
                        .setHeaders(new HttpHeaders()
                                .set("Authorization", Collections.singletonList(token))
                        );
        System.out.println(request.execute().parseAsString());
    }

    @Test
    public void testPutMethod() throws IOException, ParseException {
        HttpTransport transport = new NetHttpTransport();
        HttpRequestFactory requestFactory = transport.createRequestFactory();

        GenericUrl url = new GenericUrl(connectionUtils.getUrl() + "userstories/10");
        String token = "Bearer " + authenticate().getKey();

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
    public void getLoginToken() throws IOException, ParseException {
        Token token = authenticate();

        System.out.println(token);
    }


    public Token authenticate() throws IOException, ParseException {

        HttpTransport transport = new NetHttpTransport();
        HttpRequestFactory requestFactory = transport.createRequestFactory();

        Credentials credentials = connectionUtils.getCredentials();
        String urlFromFile = connectionUtils.getUrl() + "/authentication";
        GenericUrl url = new GenericUrl(urlFromFile);

        HttpRequest request =
                requestFactory
                        .buildPostRequest(url, new ByteArrayContent("application/json", mapper.writeValueAsString(credentials).getBytes()));

        String token = request.execute().parseAsString();

        //try to make token from json
        Token token1 = mapper.readValue(token, Token.class);
        System.out.println(token1);
        return token1;

    }

}
