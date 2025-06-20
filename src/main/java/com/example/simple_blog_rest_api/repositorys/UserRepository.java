package com.example.simple_blog_rest_api.repositorys;

import com.example.simple_blog_rest_api.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

    boolean existsByEmail(String email);
    User findByEmail(String email);

}
