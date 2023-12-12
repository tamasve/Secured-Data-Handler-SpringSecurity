package com.secureddatahandlerspringbe.security;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/", "/good", "/bad", "/registrate", "/mail", "/activate").permitAll()
                .requestMatchers("/home", "/users", "/contracts").authenticated()           //.hasRole("USER")   // -> ROLE_USER!
                .and().formLogin()
                .and().build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDataUserDetailsService();
    }
//    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
//
//        UserDetails user = User
//                .withUsername("tamas")
//                .password(passwordEncoder.encode("1234"))
//                .roles("USER")              // -> ROLE_USER!
//                .build();
//        UserDetails admin = User
//                .withUsername("admin")
//                .password(passwordEncoder.encode("1234"))
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(user, admin);
//    }

    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    // Load initial user data
    @Bean
    public CommandLineRunner userDataLoader(UserDataRepository userDataRepository, PasswordEncoder passwordEncoder)  {

        return (args) -> {
            if (userDataRepository.findAll().isEmpty()) {
                userDataRepository.save( new UserData(1, "tamas", "t@gmail.com", passwordEncoder.encode("1234"), "ROLE_USER") );
                userDataRepository.save( new UserData(2, "admin", "a@gmail.com", passwordEncoder.encode("1234"), "ROLE_ADMIN") );
                userDataRepository.save( new UserData(3, "mixed", "m@gmail.com", passwordEncoder.encode("1234"), "ROLE_USER,ROLE_ADMIN") );
                System.out.println("3 users were saved into DB");
            }
        };
    }

}
