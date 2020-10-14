package com.training.springproject.controller;

import com.training.springproject.dto.CourseDTO;
import com.training.springproject.dto.CoursesDTO;
import com.training.springproject.dto.UsersDTO;
import com.training.springproject.entity.Course;
import com.training.springproject.entity.Role;
import com.training.springproject.entity.User;
import com.training.springproject.exceptions.NoSuchCourseException;
import com.training.springproject.service.CourseService;
import com.training.springproject.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
        public String updateCourse(@AuthenticationPrincipal User user,
                @RequestParam Integer courseId,
                @RequestParam Map<String, String> form, Model model) throws Exception {
        form.remove("_csrf");
            List res = ControllerUtils.checkCourseEditIncorrect(
                    form, courseService.findById(courseId).orElseThrow(
                            ()->new NoSuchCourseException("Course does not exist")));

            if(!(boolean)res.get(1)){
                model.mergeAttributes((Map)res.get(0));
                Course course = courseService.findById(courseId).get();
                model.addAttribute("course", course);
                UsersDTO teachers = userService.getAllTeachers();
                model.addAttribute("teachers", teachers);
                return "courseEdit";
            }

        courseService.editCourse(courseId, form, user.getId());

        return "redirect:/courses";
    }

    @PostMapping("/delete/{id}")
    public String deleteCourse(@AuthenticationPrincipal User user,
                               @PathVariable Integer id, Model model){
        Course course = courseService.findById(id).orElseThrow(()-> new NoSuchCourseException("Course is not found"));
        if (course.isFinished()||course.isStarted()){
            model.addAttribute("messageDelete", "Finished or started course cannot be deleted");
            return "courses";
        }
        courseService.delete(id);
            logger.info("Course id " + id + "deleted by " + user.getId());
            return "redirect:/courses";
        }


}
