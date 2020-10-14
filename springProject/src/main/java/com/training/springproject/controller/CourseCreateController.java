package com.training.springproject.controller;

import com.training.springproject.dto.UsersDTO;
import com.training.springproject.entity.Course;
import com.training.springproject.entity.User;
import com.training.springproject.service.CourseService;
import com.training.springproject.service.UserService;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static java.time.temporal.ChronoUnit.DAYS;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/admincreate")
public class CourseCreateController {
    static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger("eventLogger");
    private final CourseService courseService;
    private final UserService userService;
    private final MessageSource messageSource;
    private final ControllerUtils controllerUtils;

    public CourseCreateController(CourseService courseService, UserService userService, MessageSource messageSource, ControllerUtils controllerUtils) {
        this.messageSource = messageSource;
        this.courseService = courseService;
        this.userService = userService;
        this.controllerUtils = controllerUtils;
    }
    @GetMapping
    public String addCourseForm(Model model){
        UsersDTO teachers = userService.getAllTeachers();
        model.addAttribute("teachers", teachers);
        return "AdminCourse";
    }

    @PostMapping
    public String addCourse(@AuthenticationPrincipal User user,
            @RequestParam Map<String, String> form,
            Model model, Locale locale) {
        form.remove("_csrf");
            model.mergeAttributes(form);
            UsersDTO teachers = userService.getAllTeachers();
            model.addAttribute("teachers", teachers);

        List res = controllerUtils.checkCourseIncorrect(form, locale);
        if(!(boolean)res.get(1)){
            model.mergeAttributes((Map)res.get(0));
            return "AdminCourse";
        }

        User teacher = userService.findbyId(Long.parseLong(form.get("teacherId")));
        if(courseService.checkNameDateTeacher(form.get("name"), form.get("startDate"), teacher)){
            model.addAttribute("CourseNameDateTeacherPresent", messageSource.getMessage("courAlEx", null, locale));
            model.mergeAttributes((Map)res.get(0));
            return "AdminCourse";
        }

        courseService.saveNewCourse(Course.builder()
                .name(form.get("name"))
                .nameukr(form.get("nameukr"))
                .topic(form.get("topic"))
                .topicukr(form.get("topicukr"))
                .startDate(LocalDate.parse(form.get("startDate")).plusDays(1))
                .duration(DAYS.between(LocalDate.parse(form.get("startDate")), LocalDate.parse(form.get("endDate")).plusDays(1)))
                .endDate(LocalDate.parse(form.get("endDate")))
                .teacher(teacher)
                .build(), user.getId()
        );

        return "redirect:/courses";
    }

}

