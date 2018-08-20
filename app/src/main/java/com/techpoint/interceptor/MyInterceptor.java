package com.techpoint.interceptor;

import com.techpoint.service.GeoIPLocationService;
import com.techpoint.model.GeoIP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class MyInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyInterceptor.class);
    private final GeoIPLocationService ipLocationService;

    public MyInterceptor(@Autowired GeoIPLocationService ipLocationService) {
        this.ipLocationService = ipLocationService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final GeoIP geoIP = ipLocationService.getGeoIP(request);

        LOGGER.debug("preHandle, By: {}, URI: {}", geoIP, request.getRequestURI());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
       // LOGGER.debug("postHandle, URI: {}", request.getRequestURI());
    }
}