package com.train.courses.controller;

import com.train.courses.entity.User;
import com.train.courses.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String addNewUser(@RequestParam String username,
                             @RequestParam String password,
                             Model model){
        System.out.println("checked");
        System.out.println(username);
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        userRepository.save(user);

        return "courses";
    }
}
