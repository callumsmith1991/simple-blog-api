package com.example.simple_blog_rest_api.controllers;

import com.example.simple_blog_rest_api.exceptions.UserNotFoundException;
import com.example.simple_blog_rest_api.models.User;
import com.example.simple_blog_rest_api.models.UserRoles;
import com.example.simple_blog_rest_api.repositorys.UserRepository;
import com.example.simple_blog_rest_api.repositorys.UserRolesRepository;
import com.example.simple_blog_rest_api.requests.CreateUserRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;

@Controller
@RequestMapping(path="/api")
public class UserController extends MainController {

    @Autowired
    public UserRepository repo;

    @Autowired
    public UserRolesRepository userRolesRepository;

    @GetMapping("/users/{userid}")
    public ResponseEntity<?> get(@PathVariable Integer userid)
    {
        try {
            Optional<User> user = this.repo.findById(userid);

            if(user.isEmpty()) {
                throw new UserNotFoundException("User Not Found");
            }

            Map userDetails = Map.of(
                    "name", user.get().getFullName(),
                    "email", user.get().getEmail(),
                    "role", user.get().getUserRole().getDescription()
            );

            return this.response(userDetails, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return this.response(Map.of("errors", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> create(@Valid @RequestBody CreateUserRequest request, BindingResult result)
    {
        try {

            if (result.hasErrors()) {

                Map<Object, Object> errors = new HashMap<>();

                for (FieldError error : result.getFieldErrors()) {
                    errors.put(error.getField(), error.getDefaultMessage());
                }

                return this.response(Map.of("errors", errors), HttpStatus.BAD_REQUEST);
            }

            if(!passwordMatches(request.getPassword(), request.getConfirmPassword())) {
                return this.response(Map.of("errors", "passwords do not match"), HttpStatus.BAD_REQUEST);
            }

            if(this.repo.existsByEmail(request.getEmail())) {
                return this.response(Map.of("errors", "email already exists"), HttpStatus.BAD_REQUEST);
            }

            User user = new User();
            user.setFirstname(request.getFirstname());
            user.setLastname(request.getLastname());
            user.setEmail(request.getEmail());

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            UserRoles userRoles = userRolesRepository.getById(1);
            user.setPassword(encoder.encode(request.getPassword()));
            user.setUserRole(userRoles);

            return this.response(this.repo.save(user), HttpStatus.CREATED);
        } catch (Exception e) {
            return this.response(Map.of("errors", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    private boolean passwordMatches(String password, String confirmPassword)
    {
        if(password == null || confirmPassword == null)
        {
            return false;
        }

        return password.equals(confirmPassword);
    }
}
