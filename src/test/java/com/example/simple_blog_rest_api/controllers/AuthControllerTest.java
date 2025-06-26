package com.example.simple_blog_rest_api.controllers;

import com.example.simple_blog_rest_api.config.auth.JwtUtil;
import com.example.simple_blog_rest_api.config.services.AuthService;
import com.example.simple_blog_rest_api.requests.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @MockBean
    private JwtUtil jwtUtil;

    private LoginRequest validLogin;

}
