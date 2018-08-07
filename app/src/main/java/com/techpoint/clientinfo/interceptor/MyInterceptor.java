package com.techpoint.clientinfo.interceptor;

import com.techpoint.clientinfo.model.GeoIP;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.techpoint.clientinfo.util.Util.getGeoIP;

public class MyInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final GeoIP geoIP = getGeoIP(request);
        System.out.println("preHandle, By: " + geoIP + ", URI: " + request.getRequestURI());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle, URI: " + request.getRequestURI());
    }
}