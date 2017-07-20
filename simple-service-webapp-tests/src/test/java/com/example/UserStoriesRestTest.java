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
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.ws.rs.NotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class UserStoriesRestTest {

    private static ObjectMapper mapper = new ObjectMapper();
    private static ConnectionUtils connectionUtils = new ConnectionUtils();
    private static Token token;
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    static {
        SimpleModule module = new SimpleModule();
        module.addSerializer(LocalDateTime.class, new CustomJsonProvider.CustomLocalDateTimeSerializer());
        module.addDeserializer(LocalDateTime.class, new CustomJsonProvider.CustomLocalDateTimeDeserializer());
        mapper.registerModule(module);
    }


    @BeforeClass
    public static void authenticate() throws IOException, ParseException {

        HttpTransport transport = new NetHttpTransport();
        HttpRequestFactory requestFactory = transport.createRequestFactory();

        Credentials credentials = connectionUtils.getCredentials();
        String urlFromFile = connectionUtils.getUrl() + "authentication";
        GenericUrl url = new GenericUrl(urlFromFile);

        HttpRequest request =
                requestFactory
                        .buildPostRequest(url, new ByteArrayContent("application/json", mapper.writeValueAsString(credentials).getBytes()));

        String jsonToken = request.execute().parseAsString();

        token = mapper.readValue(jsonToken, Token.class);
        System.out.println(token.getKey());
    }

    @Test
    public void testPostMethod() throws IOException, ParseException {


        HttpTransport transport = new NetHttpTransport();
        HttpRequestFactory requestFactory = transport.createRequestFactory();

        GenericUrl url = new GenericUrl(connectionUtils.getUrl() + "userstories");


        UserStory userStory = new UserStory(10, "newus", 10);

        String stringToken = "Bearer " + token.getKey();
        System.out.println(token.getKey());
        HttpRequest request =
                requestFactory.buildPostRequest(url, new ByteArrayContent("application/json", mapper.writeValueAsString(userStory).getBytes()))
                        .setHeaders(new HttpHeaders().set("Authorization", Collections.singletonList(stringToken)));

        String response = request.execute().parseAsString();
        System.out.println(response);
        UserStory userStory1 = mapper.readValue(response, UserStory.class);
        System.out.println(userStory1.toString());

        GenericUrl url1 = new GenericUrl(connectionUtils.getUrl() + "userstories" + "/" + userStory1.getId());
        HttpRequest request1 =
                requestFactory.buildGetRequest(url1)
                        .setHeaders(new HttpHeaders().set("Authorization", Collections.singletonList(stringToken)));
        UserStory userStory2 = mapper.readValue(request1.execute().parseAsString(), UserStory.class);
        assert userStory1.equals(userStory2);
    }

    @Test
    public void testGetMethod() throws IOException, ParseException {


        HttpTransport transport = new NetHttpTransport();
        HttpRequestFactory requestFactory = transport.createRequestFactory();

        GenericUrl url = new GenericUrl(connectionUtils.getUrl() + "userstories");
        String stringToken = "Bearer " + token.getKey();
        System.out.println(token.getKey());
        HttpRequest request =
                requestFactory
                        .buildGetRequest(url)
                        .setHeaders(new HttpHeaders()
                                .set("Authorization", Collections.singletonList(stringToken))
                        );

        Collection<UserStory> userStories = mapper.readValue(request.execute().parseAsString(), new TypeReference<List<UserStory>>() {
        });

        for (UserStory us : userStories) {
            System.out.println(us.toString());
        }
    }


    @Test
    public void testPutMethod() throws IOException, ParseException {
        HttpTransport transport = new NetHttpTransport();
        HttpRequestFactory requestFactory = transport.createRequestFactory();

        GenericUrl url = new GenericUrl(connectionUtils.getUrl() + "userstories/0");
        String stringToken = "Bearer " + token.getKey();
        System.out.println(token.getKey());
        UserStory userStory = new UserStory(10, "newus 22", 1011);
        HttpRequest request =
                requestFactory
                        .buildPutRequest(url, new ByteArrayContent("application/json", mapper.writeValueAsString(userStory).getBytes()))
                        .setHeaders(new HttpHeaders()
                                .set("Authorization", Collections.singletonList(stringToken))
                        );
        String response = request.execute().parseAsString();
        UserStory userStory1 = mapper.readValue(response, UserStory.class);
        System.out.println(response);

        GenericUrl url1 = new GenericUrl(connectionUtils.getUrl() + "userstories" + "/0");
        HttpRequest request1 =
                requestFactory.buildGetRequest(url1)
                        .setHeaders(new HttpHeaders().set("Authorization", Collections.singletonList(stringToken)));
        UserStory userStory2 = mapper.readValue(request1.execute().parseAsString(), UserStory.class);
        assert userStory1.equals(userStory2);
    }

    @Test
    public void testDeleteMethod() throws IOException, ParseException {

        HttpTransport transport = new NetHttpTransport();
        HttpRequestFactory requestFactory = transport.createRequestFactory();

        GenericUrl url = new GenericUrl(connectionUtils.getUrl() + "userstories/0");
        String stringToken = "Bearer " + token.getKey();
        System.out.println(token.getKey());
        HttpRequest request =
                requestFactory
                        .buildDeleteRequest(url)
                        .setHeaders(new HttpHeaders()
                                .set("Authorization", Collections.singletonList(stringToken))
                        );
        System.out.println(request.execute().parseAsString());

        GenericUrl url1 = new GenericUrl(connectionUtils.getUrl() + "userstories");
        HttpRequest request1 =
                requestFactory
                        .buildGetRequest(url)
                        .setHeaders(new HttpHeaders()
                                .set("Authorization", Collections.singletonList(stringToken))
                        );
        exception.expect(HttpResponseException.class);
        try {
            request1.execute();
            Assert.fail("No exception");
        } catch (NotFoundException e) {
           assert true;
        }
    }


}
