package com.example.rest.config;

import com.example.rest.authentication.Token;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomJsonProvider extends JacksonJaxbJsonProvider {

    private static DateTimeFormatter formatter =  DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");

    private static final ObjectMapper mapper = new ObjectMapper();
    static {
        SimpleModule module = new SimpleModule();
        module.addSerializer(LocalDateTime.class, new CustomLocalDateTimeSerializer());
        module.addDeserializer(LocalDateTime.class, new CustomLocalDateTimeDeserializer());
        mapper.registerModule(module);
    }

    public CustomJsonProvider() {
        super();
        setMapper(mapper);
    }

    public static class CustomLocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
        @Override
        public LocalDateTime deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
            return LocalDateTime.parse(parser.getValueAsString(),formatter) ;
        }
    }

    public static class CustomLocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {
        @Override
        public void serialize(LocalDateTime localDateTime, JsonGenerator generator, SerializerProvider serializerProvider) throws IOException {
            generator.writeString(formatter.format(localDateTime));
        }
    }

    public static void main(String[] args) throws IOException {
        Token t = new Token();
        t.setCreationTime(LocalDateTime.now());
        t.setKey("asd");
        t.setUserName("qweqw");
        String json = mapper.writeValueAsString(t);
        System.out.println(json);

        Token newToken = mapper.readValue(json, Token.class);
        System.out.println(newToken);

    }

}