package com.training.springproject.dto;

import com.training.springproject.entity.User;

import java.util.List;

public class UsersDTO {
    private List<User> users;

    public UsersDTO(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public UsersDTO() {
    }
}
