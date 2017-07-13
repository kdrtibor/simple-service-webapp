package com.example;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;


public class HttpClientTest {

    public static void main(String[] args) throws IOException {
        String PostUrl = "http://localhost:8080/dynamic";
        HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory();
        GenericUrl url = new GenericUrl(PostUrl.replaceAll(" ", "%20"));
        String requestBody = "{'from': 10,'to': 101}";
        HttpRequest request = requestFactory.buildPostRequest(url, ByteArrayContent.fromString(null, requestBody));
        request.getHeaders().setContentType("application/json");
        System.out.println("HttpRequest request" + request);
        HttpResponse response = request.execute();
        System.out.println(getStringFromStream(response.getContent()));
    }

    private static String getStringFromStream(InputStream is){
        Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

}
