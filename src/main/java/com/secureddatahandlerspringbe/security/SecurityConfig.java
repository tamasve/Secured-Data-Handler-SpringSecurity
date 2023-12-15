package com.secureddatahandlerspringbe.security;

import com.secureddatahandlerspringbe.entity.UserData;
import com.secureddatahandlerspringbe.repository.UserDataRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/css/**", "/", "/main", "/data", "/loginn", "/bad", "/mail",
                        "/reg", "/registration", "/registrate", "/activation/**", "/error").permitAll()
                .requestMatchers("/home", "/users", "/contracts", "/books").authenticated()           //.hasRole("USER")   // -> ROLE_USER!
                .and().formLogin(
//                        login -> login
//                        .loginPage("/login") 				// we give an own login html site - this will ask for login data and send them back to Spring Sec.
//                        .permitAll()
                        )
                .and()
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDataUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    // Load initial user data if User table in DB is void
    @Bean
    public CommandLineRunner userDataLoader(UserDataRepository userDataRepository, PasswordEncoder passwordEncoder)  {

        return (args) -> {
            if (userDataRepository.findAll().isEmpty()) {
                userDataRepository.save( new UserData(1, "tamas", "t@gmail.com", passwordEncoder.encode("1234"), "ROLE_USER", "", true) );
                userDataRepository.save( new UserData(2, "admin", "a@gmail.com", passwordEncoder.encode("1234"), "ROLE_ADMIN", "", true) );
                userDataRepository.save( new UserData(3, "mixed", "m@gmail.com", passwordEncoder.encode("1234"), "ROLE_USER,ROLE_ADMIN", "", true) );
                System.out.println("3 users were saved into DB");
            }
        };
    }

}
