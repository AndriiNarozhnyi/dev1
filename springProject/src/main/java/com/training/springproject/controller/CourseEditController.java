package com.training.springproject.controller;

import com.training.springproject.dto.CourseDTO;
import com.training.springproject.dto.CoursesDTO;
import com.training.springproject.dto.UsersDTO;
import com.training.springproject.entity.Course;
import com.training.springproject.entity.Role;
import com.training.springproject.entity.User;
import com.training.springproject.service.CourseService;
import com.training.springproject.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class CourseEditController {
    private final CourseService courseService;
    private final UserService userService;

    public CourseEditController(CourseService courseService, UserService userService) {
        this.courseService = courseService;
        this.userService = userService;
    }
    @GetMapping("/admin/courses")
    public String addCourse(Model model){
        CoursesDTO courses = courseService.getAllCourses();
        model.addAttribute("courses", courses);
        UsersDTO teachers = userService.getAllTeachers();
        model.addAttribute("teachers", teachers);

        return "AdminCourse";
    }

    @GetMapping("/admin/{id}")
    public String userEditForm(@PathVariable Integer id, Model model){
        Course course = courseService.findById(id).get();
        model.addAttribute("course", course);
        UsersDTO teachers = userService.getAllTeachers();
        model.addAttribute("teachers", teachers);
    return "courseEdit";
    }
    @PostMapping("/admin/courses")
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
