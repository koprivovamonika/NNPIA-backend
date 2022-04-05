package com.example.nnpia_sem_backend.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestContr {

    @RequestMapping(value = "/custom", method = RequestMethod.GET)
    public String custom() {
        return "custom";
    }

}
