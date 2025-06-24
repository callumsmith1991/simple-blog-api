package com.example.simple_blog_rest_api.config;

import com.example.simple_blog_rest_api.config.auth.AuthEntryPointJwt;
import com.example.simple_blog_rest_api.middleware.AdminUserFilter;
import com.example.simple_blog_rest_api.middleware.AuthTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

        @Autowired
        private AuthEntryPointJwt unauthorizedHandler;

        @Autowired
        AuthTokenFilter authenticationJwtTokenFilter;

        @Autowired
        AdminUserFilter adminUserFilter;

        @Autowired
        AuthenticationEntryPoint authenticationEntryPoint;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            return http
                    .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
                    .and()
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers("/api/login").permitAll()
                            .requestMatchers("/api/users/admin/**").authenticated()
                            .anyRequest().authenticated()
                    )
                    .csrf(csrf -> csrf.disable()) // disable CSRF for APIs
                    .formLogin(form -> form.disable())
                    .httpBasic(Customizer.withDefaults()) // enable HTTP Basic auth
                    // Add the JWT Token filter before the UsernamePasswordAuthenticationFilter
                    .addFilterBefore(authenticationJwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                    .addFilterBefore(adminUserFilter, UsernamePasswordAuthenticationFilter.class)
                    .build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
           return new BCryptPasswordEncoder();
        }
    }

