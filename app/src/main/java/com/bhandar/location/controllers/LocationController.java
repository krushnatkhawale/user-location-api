package com.bhandar.location.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.bhandar.location.services.LocationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping("/processLocation")
    public void processLocation(@RequestBody JsonNode jsonNode) {
        locationService.processLocation(jsonNode);
    }
}
