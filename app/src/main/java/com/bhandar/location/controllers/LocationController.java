package com.bhandar.location.controllers;

import com.bhandar.location.services.LocationService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/totalVisits")
    public ResponseEntity<Integer> totalVisits() {
        return new ResponseEntity<>(locationService.totalVisits(), HttpStatus.OK);
    }
}