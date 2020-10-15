package com.training.springproject.service;

import com.training.springproject.dto.CoursesDTO;
import com.training.springproject.dto.UserDTO;
import com.training.springproject.dto.UsersDTO;
import com.training.springproject.entity.Role;
import com.training.springproject.entity.User;
import com.training.springproject.exceptions.NoSuchActiveUserException;
import com.training.springproject.exceptions.NoSuchCourseException;
import com.training.springproject.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.expression.ParseException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ModelMapper modelMapper;

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public Page<UserDTO> findAllUsers(Pageable pageable){
        Page<User> userPage = userRepository.findAllByUsernameLike("%", pageable);
        List<UserDTO> dtos = new ArrayList<>();
        for (User user : userPage){
            dtos.add(convertToDto(user));
        }
        return new PageImpl<>(dtos, pageable, userPage.getTotalElements());
    }
    public UserDTO findUserByUserName(String username){
        return convertToDto(userRepository.findByUsername(username));
    }

    public UsersDTO getAllTeachers() {
        UsersDTO udto = new UsersDTO(userRepository.findByRoleTeacher().stream()
                .map(this::convertToDto).collect(Collectors.toList()));
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
    public void saveEdited(UserDTO userDTO, Map<String, String> form, UserDTO editedUser){
        User user = convertToUser(userDTO);

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

    public Page<UserDTO> findUsersByFilter(String fusername, String fusernameukr, Pageable pageable) {
        Page<User> userPage = userRepository.findByUsernameLikeAndUsernameukrLike(
                "%"+fusername+"%", "%"+fusernameukr+"%", pageable);
        List<UserDTO> dtos = new ArrayList<>();
        for (User user : userPage){
            dtos.add(convertToDto(user));
        }
        return new PageImpl<>(dtos, pageable, userPage.getTotalElements());

    }
    private UserDTO convertToDto(User user) {
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        return userDTO;
    }

    private User convertToUser(UserDTO userDTO) throws ParseException {
        User user = modelMapper.map(userDTO, User.class);
        return user;
    }

    public UserDTO findbyIdU(Long userId) throws Exception {
        return convertToDto(userRepository.findById(userId).orElseThrow(()->new Exception("No user with such Id")));
    }
}
