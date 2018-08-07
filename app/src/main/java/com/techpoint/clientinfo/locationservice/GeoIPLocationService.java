package com.techpoint.clientinfo.locationservice;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.techpoint.clientinfo.model.GeoIP;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

public class GeoIPLocationService {
    private static DatabaseReader dbReader;
    static{
        try {
            final ClassPathResource classPathResource = new ClassPathResource("location\\GeoLite2-City.mmdb");
            final File database = classPathResource.getFile();
            dbReader = new DatabaseReader.Builder(database).build();
        }catch (Exception e){
            System.out.println("Exception: " + e);
        }
    }

    public static GeoIP getLocation(String ip) throws IOException, GeoIp2Exception {
        InetAddress ipAddress = InetAddress.getByName(ip);
        CityResponse response = dbReader.city(ipAddress);

        String cityName = response.getCity().getName();
        String latitude = response.getLocation().getLatitude().toString();
        String longitude = response.getLocation().getLongitude().toString();
        return new GeoIP(ip, cityName, latitude, longitude);
    }
}