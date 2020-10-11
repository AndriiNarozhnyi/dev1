package com.training.springproject.controller;


import com.training.springproject.dto.UserDTO;
import com.training.springproject.entity.Role;
import com.training.springproject.entity.User;
import com.training.springproject.repository.UserRepository;
import com.training.springproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;

@Controller
public class RegistrationController {
    org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger("eventLogger");
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private UserService userService;


    @GetMapping("/registration")
    public String registration(Model model) {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Locale locale, Model model) {

            User userFromDb = userService.findUserByUserName(user.getUsername());
            if (userFromDb != null) {
                model.addAttribute("message_pres", messageSource.getMessage("messageUserPresent", null, locale));
                model.addAttribute("put_name", user.getUsername());
                return "registration";
            }
            if(!ControllerUtils.emailValid(user.getEmail())){
                model.addAttribute("emailIncorrect", messageSource.getMessage("emailIncorrect",null,locale));
                model.addAttribute("put_email", user.getEmail());
                return "registration";
            }

            user.setActive(true);
            user.setRoles(Collections.singleton(Role.USER));

            userRepository.save(user);
            logger.info("new User " + user.toString()+ " registered");

            return "redirect:/login";

    }
}
