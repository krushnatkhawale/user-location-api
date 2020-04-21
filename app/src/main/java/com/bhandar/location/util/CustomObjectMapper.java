package com.bhandar.location.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
public class CustomObjectMapper {

    final ObjectMapper objectMapper;

    public CustomObjectMapper() {
        objectMapper = new ObjectMapper();

        JavaTimeModule module = new JavaTimeModule();
        module.addSerializer(ZonedDateTime.class, new DateAndTimeSerializer());
        module.addDeserializer(ZonedDateTime.class, new DateAndTimeDeserializer());

        objectMapper.registerModule(module);
    }

    public JsonNode stringToJson(String string) {
        try {
            return objectMapper.readTree(string);
        } catch (JsonProcessingException e) {
            return objectMapper.createObjectNode();
        }
    }
}
