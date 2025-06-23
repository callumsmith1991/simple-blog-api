package com.example.simple_blog_rest_api.config.auth;

import com.example.simple_blog_rest_api.models.User;
import com.example.simple_blog_rest_api.repositorys.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import java.util.Collections;

// https://medium.com/@victoronu/implementing-jwt-authentication-in-a-simple-spring-boot-application-with-java-b3135dbdb17b
@Service
public class CustomUserDetailsService  {
    @Autowired
    private UserRepository userRepository;

    public UserDetails loadUserByEmail(String email) {

        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User Not Found with email: " + email);
        }

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.emptyList()
        );
    }
}