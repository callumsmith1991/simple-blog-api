package com.example.simple_blog_rest_api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue()
    private Integer userid;

    private String firstname;

    private String lastname;

    private String email;

    private String password;

    @OneToOne
    @JoinColumn(name = "roleid")
    @JsonIgnore
    private UserRoles userRole;

    public UserRoles getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRoles userRole) {
        this.userRole = userRole;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName()
    {
        return this.firstname+" "+this.lastname;
    }
}
