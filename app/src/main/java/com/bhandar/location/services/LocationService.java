package com.bhandar.location.services;

import com.bhandar.location.client.LocationClient;
import com.bhandar.location.util.CustomObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LocationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocationService.class);
    private static final String IP_DATABASE_FILE = "ip-database.dat";
    private final LocationClient locationClient;
    private CustomObjectMapper customObjectMapper;
    private List<JsonNode> locations;
    private List<JsonNode> newIps = new ArrayList<>();

    public LocationService(LocationClient locationClient, CustomObjectMapper customObjectMapper) {
        this.locationClient = locationClient;
        this.customObjectMapper = customObjectMapper;
    }

    @PostConstruct
    public void loadLocations() throws IOException {
        File file = new File(IP_DATABASE_FILE);
        if (!file.exists()) {
            file.createNewFile();
        }
        locations = Files.lines(Paths.get(IP_DATABASE_FILE))
                .map(customObjectMapper::stringToJson)
                .collect(Collectors.toList());
        LOGGER.info("Loaded {} ips from file", locations.size());
    }

    @Scheduled(fixedDelay = 5 * 1000)
    public synchronized void dumpLocationsToFile() {
        try {
            if (!newIps.isEmpty()) {
                Files.write(Paths.get(IP_DATABASE_FILE), newIps.stream().map(Objects::toString).collect(Collectors.toList()));
                LOGGER.info("{} new IP's written to file", newIps.size());
                locations.addAll(newIps);
                newIps = new ArrayList<>();
            }
        } catch (IOException e) {
            LOGGER.info("Error while writing {} IP's to file: ", newIps.size(), e);
            newIps.forEach(ipInfo -> LOGGER.info("Logging IPto the console: {}", ipInfo.toString()));
        }
    }

    public void processLocation(JsonNode jsonNode) {
        final String clientIp = jsonNode.get("ip").asText();

        Optional<JsonNode> ip = getIp(clientIp);

        JsonNode timestampedIpInfo = ip.map(this::timestampedIpInfo).orElseGet(() -> timestampedIpInfo(locationClient.getLocation(jsonNode)));

        newIps.add(timestampedIpInfo);
    }

    private JsonNode timestampedIpInfo(JsonNode jsonNode) {
        JsonNode tempNode = jsonNode.deepCopy();
        ((ObjectNode) tempNode).put("when", ZonedDateTime.now().toString());
        return tempNode;
    }

    private Optional<JsonNode> getIp(String clientIp) {
        return locations.stream()
                .filter(record -> record.get("ip").asText().equals(clientIp))
                .findFirst();
    }
}