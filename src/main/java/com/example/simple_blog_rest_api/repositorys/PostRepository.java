package com.example.simple_blog_rest_api.repositorys;

import com.example.simple_blog_rest_api.models.Post;
import com.example.simple_blog_rest_api.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Integer> {

    List<Post> findAllByUser(User user);
}
