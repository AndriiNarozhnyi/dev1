package com.training.springproject.dto;

import com.training.springproject.entity.Role;

import javax.persistence.*;
import java.util.Set;

public class UserDTO {
    private Long id;
    private String username;
    private String username_ukr;
    private String email;
    private String password;
    private boolean active;
    private Set<Role> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername_ukr() {
        return username_ukr;
    }

    public void setUsername_ukr(String username_ukr) {
        this.username_ukr = username_ukr;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public UserDTO(Long id, String username, String username_ukr, String email, String password, boolean active, Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.username_ukr = username_ukr;
        this.email = email;
        this.password = password;
        this.active = active;
        this.roles = roles;
    }

    public UserDTO() {
    }
}
