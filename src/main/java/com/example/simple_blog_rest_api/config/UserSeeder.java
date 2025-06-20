package com.example.simple_blog_rest_api.config;

import com.example.simple_blog_rest_api.models.User;
import com.example.simple_blog_rest_api.models.UserRoles;
import com.example.simple_blog_rest_api.repositorys.UserRepository;
import com.example.simple_blog_rest_api.repositorys.UserRolesRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile("dev")
public class UserSeeder {

    private PasswordEncoder passwordEncoder;

    public UserSeeder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public CommandLineRunner initAdminUser(UserRepository userRepo, PasswordEncoder encoder, UserRolesRepository userRolesRepo)
    {
        return args -> {

            UserRoles adminRole = new UserRoles();
            adminRole.setId(1);
            adminRole.setDescription("Admin");

            UserRoles userRole = new UserRoles();
            userRole.setId(2);
            userRole.setDescription("User");

            if(userRolesRepo.findById(1).isEmpty() && userRolesRepo.findById(2).isEmpty()) {
               userRolesRepo.save(adminRole);
               userRolesRepo.save(userRole);
            }

            if (!userRepo.existsByEmail("callummckeeversmith1804@gmail.com")) {
                User admin = new User();
                admin.setFirstname("Callum");
                admin.setLastname("Smith");
                admin.setEmail("callummckeeversmith1804@gmail.com");

                String password = encoder.encode("testfordev");
                admin.setPassword(password);
                admin.setUserRole(adminRole);
                userRepo.save(admin);
                System.out.println("Admin user created successfully");
            }
        };
    }
}
