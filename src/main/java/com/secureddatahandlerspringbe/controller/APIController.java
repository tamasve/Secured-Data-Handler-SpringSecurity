package com.secureddatahandlerspringbe.controller;

import com.secureddatahandlerspringbe.security.UserData;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class APIController {

    @GetMapping("/loginn")
    public String login() {
        return "login";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new UserData());
        return "registration";
    }

    @PostMapping("/reg")
    public String reg(@ModelAttribute UserData user) {
        System.out.println(user.getName());
        return "index";
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/main")
    public String mainPage() {
        return "main";
    }
}
