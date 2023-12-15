package com.secureddatahandlerspringbe.service;

import com.secureddatahandlerspringbe.entity.UserData;

public interface UserService {

    public String registerUser (UserData userData);

    public String userActivation (String code);

    public UserData findByUsername (String username);

    public UserData findByEmail (String email);

}
