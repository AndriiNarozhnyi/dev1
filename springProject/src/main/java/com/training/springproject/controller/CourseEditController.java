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

import static java.time.temporal.ChronoUnit.DAYS;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/adminupdate")
public class CourseEditController {
    static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger("eventLogger");
    private final CourseService courseService;
    private final UserService userService;

    public CourseEditController(CourseService courseService, UserService userService) {
        this.courseService = courseService;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public String courseEditForm(@PathVariable Integer id, Model model){
        Course course = courseService.findById(id).get();
        model.addAttribute("course", course);
        UsersDTO teachers = userService.getAllTeachers();
        model.addAttribute("teachers", teachers);
    return "courseEdit";
    }

        @PostMapping
        public String updateCourse(
                //is it ok to get course like that or it is better to make whole update by native query "update"?
                @RequestParam Integer courseId,
                @RequestParam Map<String, String> form, Model model) throws Exception {
        courseService.editCourse(courseId, form);
        return "redirect:/courses";
        }


}
