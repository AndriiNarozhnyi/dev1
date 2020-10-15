package com.training.springproject.controller;

import com.training.springproject.entity.Role;
import com.training.springproject.entity.User;
import com.training.springproject.service.UserService;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Locale;
import java.util.Map;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {
    private final MessageSource messageSource;
    private final UserService userService;
    private final ControllerUtils controllerUtils;

    public UserController(MessageSource messageSource, UserService userService, ControllerUtils controllerUtils) {
        this.messageSource = messageSource;
        this.userService = userService;
        this.controllerUtils = controllerUtils;
    }

    @GetMapping("/user/filter")
    public String userList(Model model,
                           @PageableDefault(sort = {"id"},
                                   direction = Sort.Direction.ASC) Pageable pageable,
                           @RequestParam String fusername,
                           @RequestParam String fusernameukr){
        StringBuilder url = new StringBuilder("/user/filter");
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

    @GetMapping("/user/{user}")
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
            Map<String, String> errorsMap = controllerUtils.getErrors(bindingResult, locale);
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
