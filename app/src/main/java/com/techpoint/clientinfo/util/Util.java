package com.techpoint.interceptor;

import javax.servlet.http.HttpServletRequest;

import static java.util.Objects.isNull;
import static org.thymeleaf.util.StringUtils.isEmpty;

public class Util {
    static String getClientIp(HttpServletRequest request) {
        String remoteAddr = "";
        if (!isNull(request)) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (isEmpty(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            } else
                remoteAddr = request.getHeader("x-real-ip");
        }
        return remoteAddr;
    }
}