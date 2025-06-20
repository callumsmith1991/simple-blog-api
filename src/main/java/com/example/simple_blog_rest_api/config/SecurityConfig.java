package com.example.simple_blog_rest_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            return http
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers("/api/login").permitAll()
                            .anyRequest().authenticated()
                    )
                    .csrf(csrf -> csrf.disable()) // disable CSRF for APIs
                    .formLogin(form -> form.disable())
                    .httpBasic(Customizer.withDefaults()) // enable HTTP Basic auth
                    .build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
           return new BCryptPasswordEncoder();
        }
    }

