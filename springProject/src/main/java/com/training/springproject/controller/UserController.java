package com.training.springproject.controller;

import com.training.springproject.entity.Role;
import com.training.springproject.entity.User;
import com.training.springproject.repository.UserRepository;
import com.training.springproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URL;
import java.util.*;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;


    @GetMapping("/user/filter")
    public String userList(Model model,
                           @PageableDefault(sort = {"id"},
                                   direction = Sort.Direction.ASC) Pageable pageable,
                           @RequestParam String fusername,
                           @RequestParam String fusernameukr){
//        String url = httpServletRequest.getRequestURI();
//        Map <String, String> query = ControllerUtils.mapQuery(url);
        StringBuilder url = new StringBuilder("/user/filter");
//        if (fusernameukr.indexOf("?")!=-1) {
//            String fusernameukr1 = fusernameukr.substring(0, fusernameukr.indexOf("?"));
//            fusernameukr = fusernameukr1;
//        }


        url.append("?fusername="+fusername);
        url.append("&fusernameukr="+fusernameukr);

        if (fusername==null||fusernameukr==null){
            model.addAttribute("page", userService.findAllUsers(pageable));
        } else {
            model.addAttribute("page", userService.findUsersByFilter(fusername, fusernameukr, pageable));
        }
        model.addAttribute("fusername", fusername);
        model.addAttribute("fusernameukr", fusernameukr);
        model.addAttribute("am", url.toString().contains("?")?1:0);
        model.addAttribute("url", url.toString());
        return "userlist";
    }

    @GetMapping("/user")
    public String userList(Model model,
                           @PageableDefault(sort = {"id"},
                                   direction = Sort.Direction.ASC) Pageable pageable){
        Page<User> page = userService.findAllUsers(pageable);
        model.addAttribute("page", page);
        model.addAttribute("url", "/user");
        return "userlist";
    }

    @GetMapping("/users/{user}")
    public String userEditForm(@PathVariable User user, Model model){
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    @PostMapping("/user/save")
    public String userSave(@Valid User editedUser,
                           BindingResult bindingResult,
                           @RequestParam Map<String, String> form,
                           @RequestParam("userId") User user,
                            Model model, Locale locale){
        if(bindingResult.hasErrors()){
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.addAttribute("error", errorsMap);
            model.addAttribute("user", user);
            model.addAttribute("roles", Role.values());
            return "userEdit";
        } else {

            userService.saveEdited(user, form, editedUser);
        }
        return "redirect:/user";
    }
}
