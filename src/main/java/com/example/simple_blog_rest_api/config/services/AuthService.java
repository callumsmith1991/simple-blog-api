package com.example.simple_blog_rest_api.config.services;

import com.example.simple_blog_rest_api.models.User;
import com.example.simple_blog_rest_api.repositorys.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder encoder;

    public boolean authenticate(String email, String password)
    {
        User user = userRepo.findByEmail(email);

        if(user != null) {
            return encoder.matches(password, user.getPassword());
        }

        return false;
    }
}
