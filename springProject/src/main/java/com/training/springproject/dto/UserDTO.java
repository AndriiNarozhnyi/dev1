package com.training.springproject.dto;

import com.training.springproject.entity.Course;
import com.training.springproject.entity.Role;
import com.training.springproject.entity.User;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class UserDTO {
    private Long id;
    private String username;
    private String usernameukr;
    private String email;
    private boolean active;
    private Set<Role> roles;
    Set<Course> takenCourses = new HashSet<>();

    public Set<Course> getTakenCourses() {
        return takenCourses;
    }

    public void setTakenCourses(Set<Course> takenCourses) {
        this.takenCourses = takenCourses;
    }

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

    public String getUsernameukr() {
        return usernameukr;
    }

    public void setUsernameukr(String username_ukr) {
        this.usernameukr = username_ukr;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public boolean isAdmin() {
        return roles.contains(Role.ADMIN);
    }

    public boolean isTeacher() {
        return roles.contains(Role.TEACHER);
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        UserDTO userDTO = (UserDTO) o;
        return username.equals(userDTO.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    public boolean isStudent(){
        return roles.contains(Role.USER);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", username_ukr='" + usernameukr + '\'' +
                ", email='" + email + '\'' +
                ", active=" + active +
                ", roles=" + roles +
                '}';
    }
}
