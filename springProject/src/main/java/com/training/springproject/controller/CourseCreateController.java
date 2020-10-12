package com.training.springproject.controller;

import com.training.springproject.dto.UsersDTO;
import com.training.springproject.entity.Course;
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
@RequestMapping("/admincreate")
public class CourseCreateController {
    static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger("eventLogger");
    private final CourseService courseService;
    private final UserService userService;

    public CourseCreateController(CourseService courseService, UserService userService) {
        this.courseService = courseService;
        this.userService = userService;
    }
    @GetMapping
    public String addCourseForm(Model model){
        UsersDTO teachers = userService.getAllTeachers();
        model.addAttribute("teachers", teachers);
        return "AdminCourse";
    }

    @PostMapping
    public String addCourse(
            @RequestParam Map<String, String> form,
            Model model) {
        form.remove("_csrf");
            model.mergeAttributes(form);
            UsersDTO teachers = userService.getAllTeachers();
            model.addAttribute("teachers", teachers);

        List res = ControllerUtils.checkCourseIncorrect(form);
        if(!(boolean)res.get(1)){
            model.mergeAttributes((Map)res.get(0));
            return "AdminCourse";
        }

        User teacher = userService.findbyId(Long.parseLong(form.get("teacherId")));
        if(courseService.checkNameDateTeacher(form.get("name"), form.get("startDate"), teacher)){
            model.addAttribute("CourseNameDateTeacherPresent", "Course with such name teacher and start date already exists!");
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
                .build()
        );
        return "redirect:/courses";
    }

}

