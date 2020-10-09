package com.training.springproject.service;

import com.training.springproject.dto.CoursesDTO;
import com.training.springproject.dto.UserDTO;
import com.training.springproject.dto.UsersDTO;
import com.training.springproject.entity.Role;
import com.training.springproject.entity.User;
import com.training.springproject.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }
    public UsersDTO getAllUsers() {
        //TODO checking for an empty user list
        return new UsersDTO(userRepository.findAll());
    }
    public User findUserByUserName(String username){
        return userRepository.findByUsername(username);
    }

    public UsersDTO getAllTeachers() {
        UsersDTO udto = new UsersDTO(userRepository.findByRoleTeacher());
        return udto;
    }

    public void update(User user) {
        userRepository.save(user);
    }
}
