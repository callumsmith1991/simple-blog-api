package com.example.simple_blog_rest_api.controllers;

import com.example.simple_blog_rest_api.models.User;
import com.example.simple_blog_rest_api.repositorys.UserRepository;
import com.example.simple_blog_rest_api.requests.CreateUserRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;

@Controller
@RequestMapping(path="/api/users")
public class UserController extends MainController {

    @Autowired
    public UserRepository repo;

    @GetMapping("/{userid}")
    public ResponseEntity<?> get(@PathVariable Integer userid)
    {
        Optional<User> user = this.repo.findById(userid);
        return this.response(user, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody CreateUserRequest request, BindingResult result) {

        try {
            Map<String, String> errors = new HashMap<>();

            if (result.hasErrors()) {
                return this.response(this.getErrorMessages(result), HttpStatus.BAD_REQUEST);
            }

            if (this.repo.existsByEmail(request.getEmail())) {
                errors.put("email", "email already exists");

                return this.response(errors, HttpStatus.BAD_REQUEST);
            }

            User user = new User();
            user.setFirstname(request.getFirstname());
            user.setLastname(request.getLastname());
            user.setEmail(request.getEmail());

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
            String password = encoder.encode("test");

            user.setPassword(password);
            User savedUser = this.repo.save(user);
            return this.response(savedUser, HttpStatus.CREATED);
        } catch (Exception e) {
            return this.response(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
