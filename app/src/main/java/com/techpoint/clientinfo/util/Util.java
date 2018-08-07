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

    public static GeoIP getGeoIP(final HttpServletRequest request) throws IOException, GeoIp2Exception {
        final String clientIp = getClientIp(request);
        return GeoIPLocationService.getLocation(clientIp);
    }

    private static String getClientIp(HttpServletRequest request) {
        String remoteAddr = "";
        if (!isNull(request)) {
            remoteAddr = getForwardedFor(request);
            if (isEmpty(remoteAddr)) {
                remoteAddr = getRealIp(request);
                if (isEmpty(remoteAddr)) {
                    remoteAddr = request.getRemoteAddr();
                    LOGGER.info("RemoteAddress: {}", remoteAddr);
                }
            } else {
                return remoteAddr;
            }
        }
        return remoteAddr;
    }


    private static String getForwardedFor(HttpServletRequest request) {
        String forwardedFor = request.getHeader("X-FORWARDED-FOR");
        LOGGER.info("X-FORWARDED-FOR: {}", forwardedFor);
        return forwardedFor;
    }

    private static String getRealIp(HttpServletRequest request) {
        String realIP = request.getHeader("X-REAL-IP");
        LOGGER.info("X-REAL-IP: {}", realIP);
        return realIP;
    }
}