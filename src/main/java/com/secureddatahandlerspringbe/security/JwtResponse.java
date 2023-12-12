package com.secureddatahandlerspringbe.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {

    private String accessToken;
//    final private String type = "Bearer";
//    private Long id;
    private String username;
    private String email;
//    private List<String> roles;
}
