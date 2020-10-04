package com.training.springproject.controller;


import com.training.springproject.entity.Role;
import com.training.springproject.entity.User;
import com.training.springproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/registration")
    public String registration(Model model) {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@RequestParam String username, Model model) {
        System.out.println(username);
        User userFromDb = userRepository.findByUsername(username);

        if (userFromDb != null) {
            model.addAttribute("message", "User already exists!");
            return "registration";
        }
        User user = new User();
        user.setUsername(username);

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);

        return "redirect:/login";
    }
}
