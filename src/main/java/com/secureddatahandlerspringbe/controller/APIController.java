package com.secureddatahandlerspringbe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class APIController {

    @GetMapping("/loginn")
    public String login() {
        return "login";
    }
}
