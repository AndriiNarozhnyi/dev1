package com.training.springproject.dto;

import java.util.List;

public class UsersDTO {
    private List<UserDTO> users;

    public UsersDTO(List<UserDTO> users) {
        this.users = users;
    }

    public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }

    public UsersDTO() {
    }
}
