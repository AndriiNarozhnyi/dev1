package com.training.springproject.service;

import com.training.springproject.dto.CoursesDTO;
import com.training.springproject.dto.UserDTO;
import com.training.springproject.dto.UsersDTO;
import com.training.springproject.entity.Role;
import com.training.springproject.entity.User;
import com.training.springproject.exceptions.NoSuchActiveUserException;
import com.training.springproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;

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
    public Page<User> findAllUsers(Pageable pageable){
        return userRepository.findAll(pageable);
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

    public User findbyId(Long teacherId) {
        return userRepository.findByIdAndActiveTrue(teacherId).orElseThrow(
                ()-> new NoSuchActiveUserException ("No such active teacher"));
    }

    public void save(User user, Map<String, String> form) {
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
    public void saveEdited(User user, Map<String, String> form, User editedUser){
        user.setUsername(editedUser.getUsername());
        user.setUsernameukr(editedUser.getUsernameukr());
        user.setEmail(editedUser.getEmail());

        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
        user.getRoles().clear();

        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }
        if (form.get("isActive") != null) {
            user.setActive(true);
        } else {
            user.setActive(false);
        }
        userRepository.save(user);

    }

    public Page<User> findUsersByFilter(String fusername, String fusernameukr, Pageable pageable) {
        return userRepository.findByUsernameLikeAndUsernameukrLike(
                "%"+fusername+"%", "%"+fusernameukr+"%", pageable);
    }
}
