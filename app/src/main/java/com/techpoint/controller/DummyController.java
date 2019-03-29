package com.techpoint.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static org.springframework.http.HttpStatus.OK;

@Controller
public class DummyController {

    Logger LOGGER = LoggerFactory.getLogger(DummyController.class);

    @RequestMapping(method = RequestMethod.GET, value = "/hello")
    public HttpEntity<String> helloGet() {
        LOGGER.info("/hello triggered");
        return new ResponseEntity<>("HELLO WORLD!", OK);
    }
}