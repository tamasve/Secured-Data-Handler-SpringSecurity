package com.secureddatahandlerspringbe.controller;

import com.secureddatahandlerspringbe.security.UserData;
import com.secureddatahandlerspringbe.service.BookService;
import com.secureddatahandlerspringbe.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());


    private UserService userService;
    private BookService bookService;
    @Autowired
    public void setUserService(UserService userService) { this.userService = userService; }
    @Autowired
    public void setBookService(BookService bookService) { this.bookService = bookService; }


    @GetMapping("/loginn")
    public String login() {
        return "login";
    }


    // == REGISTRATION ==

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new UserData());
        model.addAttribute("error", "");
        return "registration";
    }

    @PostMapping("/reg")
    public String reg(@ModelAttribute UserData user, Model model) {
        // check if none of the 3 main params is void - if any is void >> back to registration page
        if (user.getUsername().isEmpty() || user.getPassword().isEmpty() || user.getEmail().isEmpty()) {
            log.error("Error! - Registration data contain void param");
            model.addAttribute("user", user);
//                    new UserData(0, user.getUsername(), user.getPassword(), user.getEmail(), "", "", false));
            model.addAttribute("error", "Error! - Registration data contain void param");
            return "registration";
        };
        // Otherwise register user
        String message = userService.registerUser(user);
        log.info(message);
        // any error during service activity >> back to registration page
        if (!message.equals("OK")) {
            model.addAttribute("user", user);
//                    new UserData(0, user.getUsername(), user.getPassword(), user.getEmail(), "", "", false));
            model.addAttribute("error", message);
            return "registration";
        }
        // registration was OK
        model.addAttribute("message", "Registration was OK. You have to receive an e-mail about the activation.");
        return "index";
    }

    // user gets here after clicking onto the activation link
    @GetMapping("/activation/{code}")
    public String activation(@PathVariable("code") String code, Model model) {
        log.info("code: " + code);
        String message = userService.userActivation(code);  // find user by act.code
        log.info(message);
        if (!message.equals("OK")) {    // no user found >> error page
            model.addAttribute("header", "Error during activation!");
            model.addAttribute("message", message);
            return "error";
        }
        model.addAttribute("message", "Successful activation!");
        return "index";
    }

    // == ==

    // root page
    @GetMapping("/")
    public String index(Model model) {
        String userLoggedIn = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("user logged in: " + userLoggedIn);
        model.addAttribute("message", "");
        model.addAttribute("username", userLoggedIn);
        return "index";
    }

    @GetMapping("/books")
    public String books(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "books";
    }

    @GetMapping("/main")
    public String mainPage() {
        return "main";
    }

    @ExceptionHandler
    public String exceptionHandler(Model model, Exception exception) {
        log.error("Exception happened");
        model.addAttribute("header", "Error!");
        model.addAttribute("message", exception.getMessage());
        return "error";
    }
}
