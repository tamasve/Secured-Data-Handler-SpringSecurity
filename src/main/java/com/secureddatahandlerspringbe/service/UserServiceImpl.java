package com.secureddatahandlerspringbe.service;

import com.secureddatahandlerspringbe.security.UserData;
import com.secureddatahandlerspringbe.security.UserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    private UserDataRepository userDataRepository;
    private EmailService emailService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl (UserDataRepository userDataRepository, EmailService emailService, PasswordEncoder passwordEncoder) {
        this.userDataRepository = userDataRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String registerUser(UserData user) {
        if (findByUsername(user.getUsername()) != null) return "User already exists!";
//        UserData newUser = new UserData();
//        newUser.setUsername(user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        newUser.setEmail(user.getEmail());
        user.setRoles("USER");
        user.setActivation(getActivationCode());
        user.setEnabled(false);
        userDataRepository.save(user);
        emailService.sendEmail(
                "tamasjava@gmail.com",
                "Activation code",
                "You have to activate your account by clicking onto this link:\n\nhttp://localhost:8080/activation/" + user.getActivation());
        return "OK";
    }

    private String getActivationCode() {
        Random random = new Random();
        char[] word = new char[16];
        for (int j = 0; j < word.length; j++) {
            word[j] = (char) ('a' + random.nextInt(26));
        }
        return new String(word);
    }

    @Override
    public String userActivation(String code) {
        UserData userData = userDataRepository.findByActivation(code);
        if (userData == null)  return "No such valid activation code available";
        userData.setActivation("");
        userData.setEnabled(true);
        userDataRepository.save(userData);
        return "OK";
    }

    @Override
    public UserData findByUsername(String username) {
        return userDataRepository.findByUsername(username);
    }

    @Override
    public UserData findByEmail(String email) {
        return userDataRepository.findByEmail(email);
    }
}
