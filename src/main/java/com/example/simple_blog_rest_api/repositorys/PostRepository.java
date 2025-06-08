package com.example.simple_blog_rest_api.repositorys;

import com.example.simple_blog_rest_api.models.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Integer> {
}
