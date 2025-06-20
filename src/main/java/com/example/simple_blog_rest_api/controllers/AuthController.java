package com.example.simple_blog_rest_api.controllers;

import com.example.simple_blog_rest_api.config.Jwt;
import com.example.simple_blog_rest_api.config.services.AuthService;
import com.example.simple_blog_rest_api.requests.LoginRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path="/api")
public class AuthController extends MainController {

    @Autowired
    AuthService authService;

    @Autowired
    Jwt jwt;

    @PostMapping(path = "/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request, BindingResult result)
    {
        if(authService.authenticate(request.getEmail(), request.getPassword())) {

            Map<String,String> messages = new HashMap<>();
            messages.put("token", jwt.generateToken(request.getEmail()));
            messages.put("success", "Login Success");

            return this.response(messages, HttpStatus.OK);
        }

        return this.response(new String[]{"Auth failed"}, HttpStatus.UNAUTHORIZED);
    }

}
