package com.secureddatahandlerspringbe.security;

import com.secureddatahandlerspringbe.entity.UserData;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class UserDataUserDetails implements UserDetails {

    final private UserData userData;

    public UserDataUserDetails(UserData userData) {
        this.userData = userData;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        String [] roles = userData.getRoles().split(",");
        for (String role : roles) authorities.add(new SimpleGrantedAuthority(role));
        return authorities;
    }

    @Override
    public String getPassword() {
        return userData.getPassword();
    }

    @Override
    public String getUsername() {
        return userData.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return userData.getEnabled();
    }
}
