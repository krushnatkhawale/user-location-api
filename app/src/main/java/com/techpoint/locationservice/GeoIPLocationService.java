package com.techpoint.locationservice;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.techpoint.model.GeoIP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

import static java.util.Objects.isNull;
import static org.thymeleaf.util.StringUtils.isEmpty;

@Component
public class GeoIPLocationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GeoIPLocationService.class);
    private static final String DATABASE_FILE = "GeoLite2-City.mmdb";
    private DatabaseReader dbReader;

    public GeoIPLocationService() {
        try {
            final ClassPathResource dbResource = new ClassPathResource(DATABASE_FILE);
            dbReader = new DatabaseReader.Builder(dbResource.getInputStream()).build();
        } catch (Exception e) {
            LOGGER.error("Failed to load Geo-database", e);
        }
    }

    public GeoIP getGeoIP(final HttpServletRequest request) throws IOException, GeoIp2Exception {
        final String clientIp = getClientIp(request);
        return getLocation(clientIp);
    }

    private GeoIP getLocation(String ip) throws IOException, GeoIp2Exception {
        LOGGER.info("Extracted IP: {}", ip);
        if (ip.startsWith("172") || ip.startsWith("0")) {
            return null;
        } else {
            InetAddress ipAddress = InetAddress.getByName(ip);
            CityResponse response = dbReader.city(ipAddress);
            String cityName = response.getCity().getName();
            String latitude = response.getLocation().getLatitude().toString();
            String longitude = response.getLocation().getLongitude().toString();
            return new GeoIP(ip, cityName, latitude, longitude);
        }
    }


    private static String getClientIp(HttpServletRequest request) {
        String remoteAddr = "";
        if (!isNull(request)) {
            remoteAddr = getForwardedFor(request);
            if (isEmpty(remoteAddr)) {
                remoteAddr = getRealIp(request);
                if (isEmpty(remoteAddr)) {
                    remoteAddr = request.getRemoteAddr();
                }
            } else {
                return remoteAddr;
            }
        }
        return remoteAddr;
    }


    private static String getForwardedFor(HttpServletRequest request) {
        return request.getHeader("X-FORWARDED-FOR");
    }

    private static String getRealIp(HttpServletRequest request) {
        return request.getHeader("X-REAL-IP");
    }
}