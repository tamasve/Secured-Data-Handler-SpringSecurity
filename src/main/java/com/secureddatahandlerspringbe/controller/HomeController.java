package com.secureddatahandlerspringbe.controller;

import com.secureddatahandlerspringbe.security.JwtResponse;
import com.secureddatahandlerspringbe.security.LoginRequest;
import com.secureddatahandlerspringbe.security.RegistrationRequest;
import com.secureddatahandlerspringbe.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @Autowired
    EmailService emailService;


    @GetMapping("/")
    public String root() {
        return "This is the root page";
    }

    // how to send JS object to FE
    @GetMapping("/good")
//    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<JwtResponse> good() {
        return ResponseEntity.ok(new JwtResponse("r234r2r32tz345z356u764","john", "e@gmail.com"));
    }

    @GetMapping("/bad")
    public ResponseEntity<String> bad() {
        return ResponseEntity.badRequest().body("nothing is OK");
    }

    
    // how to receive JS object from FE
    @PostMapping("/loginuser")
    public ResponseEntity<String> loginuser(@RequestBody LoginRequest loginRequest) {
        System.out.println(loginRequest.getPassword());
        return ResponseEntity.ok("password: " + loginRequest.getPassword());
    }

    @PostMapping("/registrate")
    public ResponseEntity<String> registrate(@RequestBody RegistrationRequest registrationRequest) {
        System.out.println(registrationRequest.getPassword());
        return ResponseEntity.ok("password: " + registrationRequest.getPassword());
    }

    @GetMapping("/mail")
    public String mail() {
        emailService.sendEmail("tamasjava@gmail.com", "Activation code", "You have to activate your account by clicking onto this link:\n\nhttp://localhost:8080/activate");
        return "e-mail sent";
    }

    @GetMapping("/activate")
    public String activate() {
        return "Congratulation! You've successfully activated your account!";
    }

    @GetMapping("/home")
    @PreAuthorize("hasRole('USER')")
    public String home() {
        return "This is the secured home page - accessed by USER";
    }

    @GetMapping("/contracts")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public String contracts() {
        return "This is the contracts page, accessible by USER or ADMIN";
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public String users() {
        return "This is the secured user administrator page";
    }


}
