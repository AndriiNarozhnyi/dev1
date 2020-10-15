package com.training.springproject.controller;


import com.training.springproject.dto.UserDTO;
import com.training.springproject.entity.User;
import com.training.springproject.repository.UserRepository;
import com.training.springproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
public class RegistrationController {
    org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger("eventLogger");
    private MessageSource messageSource;
    private UserService userService;
    private ControllerUtils controllerUtils;

    public RegistrationController(MessageSource messageSource, UserService userService, ControllerUtils controllerUtils) {
        this.messageSource = messageSource;
        this.userService = userService;
        this.controllerUtils = controllerUtils;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user,
                          @RequestParam Map<String, String> form, Locale locale, Model model) {
        form.remove("_csrf");
        List res = controllerUtils.checkUserIncorrect(form, locale);
        if(!(boolean)res.get(1)){
            model.mergeAttributes((Map)res.get(0));
            model.addAllAttributes(form);
            return "registration";
        }

            UserDTO userFromDb = userService.findUserByUserName(user.getUsername());
            if (userFromDb != null) {
                model.addAttribute("message_pres", messageSource.getMessage("messageUserPresent", null, locale));
                model.mergeAttributes(form);
                model.addAttribute("putname", user.getUsername());
                return "registration";
            }

            userService.save(user, form);
            logger.info("new User " + user.toString()+ " registered");

            return "redirect:/login";

    }
}
