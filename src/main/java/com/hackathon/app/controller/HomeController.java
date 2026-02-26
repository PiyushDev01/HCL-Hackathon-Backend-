package com.hackathon.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        
        return "Welcome to the Hackathon App API! This is the home endpoint.";
    }
}
