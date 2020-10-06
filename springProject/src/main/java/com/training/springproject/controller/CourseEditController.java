package com.training.springproject.controller;

import com.training.springproject.dto.CoursesDTO;
import com.training.springproject.dto.UsersDTO;
import com.training.springproject.entity.Course;
import com.training.springproject.entity.User;
import com.training.springproject.service.CourseService;
import com.training.springproject.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class CourseEditController {
    private final CourseService courseService;
    private final UserService userService;

    public CourseEditController(CourseService courseService, UserService userService) {
        this.courseService = courseService;
        this.userService = userService;
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
}
