package com.techpoint.clientinfo.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GeoIP {
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private String ipAddress;
    private String city;
    private String latitude;
    private String longitude;

    public GeoIP() {

    }

    public GeoIP(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public GeoIP(String ipAddress, String city, String latitude, String longitude) {
        this.ipAddress = ipAddress;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        try {
            return OBJECT_MAPPER.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "Couldn't get map to json value: ";
        }
    }
}