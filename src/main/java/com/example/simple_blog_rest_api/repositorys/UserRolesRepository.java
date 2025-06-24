package com.example.simple_blog_rest_api.repositorys;

import com.example.simple_blog_rest_api.models.UserRoles;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRolesRepository extends CrudRepository<UserRoles, Integer> {

    @Query(value = "SELECT * FROM user_roles WHERE id = :id", nativeQuery = true)
    UserRoles getById(Integer id);

}
