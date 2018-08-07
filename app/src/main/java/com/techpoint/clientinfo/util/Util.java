package com.techpoint.clientinfo.util;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.techpoint.clientinfo.locationservice.GeoIPLocationService;
import com.techpoint.clientinfo.model.GeoIP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;

import static java.util.Objects.isNull;
import static org.thymeleaf.util.StringUtils.isEmpty;

public class Util {
    private static final Logger LOGGER = LoggerFactory.getLogger(Util.class);
    public static String getClientIp(HttpServletRequest request) {
        String remoteAddr = "";
        if (!isNull(request)) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (isEmpty(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            } else
                remoteAddr = request.getHeader("x-real-ip");
        }
        LOGGER.info("X-FORWARDED-FOR: {}", request.getHeader("X-FORWARDED-FOR"));
        LOGGER.info("x-forwarded-for: {}", request.getHeader("x-forwarded-for"));
        LOGGER.info("x-real-ip: {}", request.getHeader("x-real-ip"));
        LOGGER.info("X-REAL-IP: {}", request.getHeader("X-REAL-IP"));
        LOGGER.info("RemoteAddress: {}", request.getRemoteAddr());
        return remoteAddr;
    }

    public static GeoIP getGeoIP(final HttpServletRequest request) throws IOException, GeoIp2Exception {
        final String clientIp = getClientIp(request);
        return GeoIPLocationService.getLocation(clientIp);
    }
}