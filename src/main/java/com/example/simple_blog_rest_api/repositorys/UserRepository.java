package com.example.simple_blog_rest_api.repositorys;

import com.example.simple_blog_rest_api.models.User;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Integer> {

    boolean existsByEmail(String email);
    User findByEmail(String email);

    @Query(value = "SELECT COUNT(*) > 0 FROM user WHERE email = :email AND roleid = 1", nativeQuery = true)
    boolean isAdmin(@Param("email") String email);

}
