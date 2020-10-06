package com.training.springproject.controller;

import com.training.springproject.dto.CoursesDTO;
import com.training.springproject.dto.UsersDTO;
import com.training.springproject.entity.Course;
import com.training.springproject.entity.User;
import com.training.springproject.service.CourseService;
import com.training.springproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class EnterController {
    private final CourseService courseService;
    private final UserService userService;
    @Autowired
    public EnterController(CourseService courseService, UserService userService){
        this.courseService = courseService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String hello() {
        return "hello";
    }

    @GetMapping("/courses")
    public String showCourses(Model model){
        CoursesDTO courses = courseService.getAllCourses();
        model.addAttribute("courses", courses);
        UsersDTO teachers = userService.getAllTeachers();
        model.addAttribute("teachers", teachers);
        return "courses";
    }

    @PostMapping("/courses")
    public String addCourse(
            @RequestParam String name, @RequestParam String name_ukr,
                            @RequestParam String topic, @RequestParam String topic_ukr,
                            @RequestParam String startDate, @RequestParam String endDate,
                            @RequestParam ("userId") User user,
                            Model model){

    Course course = new Course(name, name_ukr, topic, topic_ukr,
            LocalDate.parse(startDate), LocalDate.parse(endDate), user);
    courseService.saveNewCourse(course);
        CoursesDTO courses = courseService.getAllCourses();
        model.addAttribute("courses", courses);
        UsersDTO teachers = userService.getAllTeachers();
        model.addAttribute("teachers", teachers);
        return "courses";

    }
    @GetMapping("/course_filter")
    public String courseFilter(@RequestParam String fname, Model model){
        CoursesDTO courses = courseService.findByNameLike(fname);
        model.addAttribute("courses", courses);
        model.addAttribute("fname", fname);
        return "courses";
    }
}
