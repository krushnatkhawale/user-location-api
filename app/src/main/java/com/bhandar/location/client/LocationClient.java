package com.bhandar.location.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class LocationClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocationClient.class);
    private RestTemplate restTemplate;
    private String locationServiceURL;

    public LocationClient(@Value("${location.client:https://freegeoip.app/json/}") String locationServiceURL) {
        this.restTemplate = new RestTemplate();
        this.locationServiceURL = locationServiceURL;
    }

    public JsonNode getLocation(JsonNode jsonNode) {
        final String clientIp = jsonNode.get("ip").asText();
        final String requestURL = jsonNode.get("url").asText();
        final String url = locationServiceURL + clientIp;
        final ResponseEntity<JsonNode> responseEntity = restTemplate.getForEntity(url, JsonNode.class);
        final HttpStatus statusCode = responseEntity.getStatusCode();

        if (statusCode.equals(HttpStatus.OK)) {
            final JsonNode resultNode = responseEntity.getBody();
            ((ObjectNode) resultNode).put("url", requestURL);
            ((ObjectNode) resultNode).remove("metro_code");

            LOGGER.info("New ip: {}, url: {}, from: {}", clientIp, requestURL, resultNode.get("city").asText());
            return resultNode;
        } else {
            LOGGER.warn("New ip: {}: Couldn't retreive location info: {}", clientIp, requestURL);
            return null;
        }
    }
}