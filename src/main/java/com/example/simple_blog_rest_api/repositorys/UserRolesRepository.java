package com.example.simple_blog_rest_api.repositorys;

import com.example.simple_blog_rest_api.models.UserRoles;
import org.springframework.data.repository.CrudRepository;

public interface UserRolesRepository extends CrudRepository<UserRoles, Integer> {
}
