package com.training.springproject.controller;

import com.training.springproject.dto.CoursesDTO;
import com.training.springproject.entity.Course;
import com.training.springproject.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class EnterController {
    private final CourseService courseService;
    @Autowired
    public EnterController(CourseService courseService){
        this.courseService = courseService;
    }

    @GetMapping("/")
    public String hello() {
        return "hello";
    }

    @GetMapping("/courses")
    public String showCourses(Model model){
        CoursesDTO courses = courseService.getAllCourses();
        model.addAttribute("courses", courses);
        return "courses";
    }

    @PostMapping("/courses")
    public String addCourse(@RequestParam String name, @RequestParam String name_ukr,
                            @RequestParam String topic, @RequestParam String topic_ukr,
                            @RequestParam String startDate, @RequestParam String endDate,
                            Model model){
        System.out.println(startDate);
        System.out.println(endDate);
        System.out.println(LocalDate.parse(startDate));

    Course course = new Course(name, name_ukr, topic, topic_ukr,
            LocalDate.parse(startDate), LocalDate.parse(endDate));
    courseService.saveNewCourse(course);
        CoursesDTO courses = courseService.getAllCourses();
        model.addAttribute("courses", courses);
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
