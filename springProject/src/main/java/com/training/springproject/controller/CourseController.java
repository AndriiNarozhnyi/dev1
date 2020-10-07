package com.training.springproject.controller;

import com.training.springproject.dto.CoursesDTO;
import com.training.springproject.dto.UsersDTO;
import com.training.springproject.entity.User;
import com.training.springproject.service.CourseService;
import com.training.springproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CourseController {
    private final CourseService courseService;
    private final UserService userService;

    @Autowired
    public CourseController(CourseService courseService, UserService userService){
        this.courseService = courseService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String hello() {
        return "hello";
    }

    @GetMapping("/courses")
    public String showCourses(@AuthenticationPrincipal User user,
            Model model){

        CoursesDTO courses = courseService.getAllCourses();
        model.addAttribute("courses", courses);
        UsersDTO teachers = userService.getAllTeachers();
        model.addAttribute("teachers", teachers);
        return "courses";
    }

    @GetMapping("/course_filter")
    public String courseFilter(@AuthenticationPrincipal User user,
            @RequestParam String fname, Model model){
        CoursesDTO courses = courseService.findByNameLike(fname);
        model.addAttribute("courses", courses);
        model.addAttribute("fname", fname);
        UsersDTO teachers = userService.getAllTeachers();
        model.addAttribute("teachers", teachers);
        return "courses";
    }

}
