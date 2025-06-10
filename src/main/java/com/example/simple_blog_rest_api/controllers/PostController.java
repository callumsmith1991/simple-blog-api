package com.example.simple_blog_rest_api.controllers;

import com.example.simple_blog_rest_api.exceptions.UserNotFoundException;
import com.example.simple_blog_rest_api.models.Post;
import com.example.simple_blog_rest_api.models.User;
import com.example.simple_blog_rest_api.repositorys.PostRepository;
import com.example.simple_blog_rest_api.repositorys.UserRepository;
import com.example.simple_blog_rest_api.requests.CreatePostRequest;
import com.sun.tools.javac.Main;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping(path="/api/posts")
public class PostController extends MainController {

    @Autowired
    public PostRepository repo;

    @Autowired
    private UserRepository userRepository;

    @PostMapping(path = "/create")
    public ResponseEntity<?> createPost(@Valid @RequestBody CreatePostRequest request, BindingResult result)
    {
        try {

            User userFound = userRepository.findById(request.getUserid()).orElseThrow(() -> new UserNotFoundException("User with id " + request.getUserid() + " not found"));
            Post post = new Post();
            post.setContent(request.getContent());
            post.setTitle(request.getTitle());
            post.setUser(userFound);
            Post savedPost = this.repo.save(post);
            return this.response(savedPost, HttpStatus.CREATED);

        } catch (UserNotFoundException e) {
            return this.response(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping(path = "/get-posts-by-user/{userid}")
    public ResponseEntity<?> getPostsByUser(@PathVariable Integer userid)
    {
        try {
            User userFound = userRepository.findById(userid).orElseThrow(() -> new UserNotFoundException("User with id " + userid + " not found"));
            return this.response(this.repo.findAllByUser(userFound), HttpStatus.FOUND);

        } catch (UserNotFoundException e) {
            return this.response(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
