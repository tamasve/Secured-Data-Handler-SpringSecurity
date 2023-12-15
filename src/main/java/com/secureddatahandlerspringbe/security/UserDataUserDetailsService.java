package com.secureddatahandlerspringbe.security;

import com.secureddatahandlerspringbe.repository.UserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDataUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDataRepository userDataRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UserDataUserDetails( userDataRepository.findByUsername(username) );
    }
}
