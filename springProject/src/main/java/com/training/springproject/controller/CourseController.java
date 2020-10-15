package com.training.springproject.controller;

import com.training.springproject.entity.Course;
import com.training.springproject.entity.User;
import com.training.springproject.service.CourseService;
import com.training.springproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
public class CourseController {
    private final CourseService courseService;
    private final UserService userService;
    private MessageSource messageSource;
    static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger("eventLogger");

    @Autowired
    public CourseController(CourseService courseService, UserService userService,
                            MessageSource messageSource){
        this.courseService = courseService;
        this.userService = userService;
        this.messageSource = messageSource;
    }

    @GetMapping("/")
    public String hello(Locale locale, Model model) {
        return "hello";
    }

    @GetMapping("/courses")
    public String showCourses (@AuthenticationPrincipal User user,
                               @PageableDefault(sort = {"startDate"},
                                       direction = Sort.Direction.ASC) Pageable pageable,
                               Model model) throws Exception{
        Page<Course> page = courseService.getAllCourses(pageable);
        model.addAttribute("page", page);
        model.addAttribute("url", "/courses");
        return "courses";
    }

    @GetMapping("/courses/filter")
    public String courseFilter(@RequestParam Map<String,String> form,
                               @PageableDefault(sort = {"startDate"},
                                       direction = Sort.Direction.ASC) Pageable pageable,
                               Model model){
        StringBuilder url = new StringBuilder("/courses/filter");
        List<Object> result = courseService.statusDispatcher(form, pageable, url);

        model.addAttribute("page", result.get(0));
        model.mergeAttributes((Map<String, String>)result.get(1));
        model.addAttribute("am", result.get(2).toString().contains("?")?1:0);
        model.addAttribute("url", result.get(2));

        return "courses";
    }

    @PostMapping("/courses/enroll")
    public String enrollCourse(@AuthenticationPrincipal User user,
                               @RequestParam Integer courseId,
                               Model model){
        courseService.enrollUser(courseId, user);
        logger.info(user.getUsername() + " enrolled for course #" + courseId);
        return "redirect:/courses";
    }

    @PostMapping("/courses/unenroll")
    public String unenrollCourse(@AuthenticationPrincipal User user,
                                 @RequestParam Integer courseId,
                               Model model){
        courseService.unenrollUser(courseId, user);
        logger.info(user.getUsername() + " unenrolled from course #" + courseId);
        return "redirect:/courses";
    }

    @PostMapping("/cabinet/unenroll")
    public String unenrollCourseCabinet(@AuthenticationPrincipal User user,
                                 @RequestParam Integer courseId,
                                 Model model){
        courseService.unenrollUser(courseId, user);
        logger.info(user.getUsername() + " unenrolled from course #" + courseId);
        return "redirect:/cabinet";
    }
    @GetMapping("/cabinet/filter")
    public String courseFilterCabinet(@AuthenticationPrincipal User user,
            @RequestParam Map<String,String> form,
                               @PageableDefault(sort = {"startDate"},
                                       direction = Sort.Direction.ASC) Pageable pageable,
                               Model model){
        StringBuilder url = new StringBuilder("/cabinet/filter");
        List<Object> result = courseService.statusDispatcher(form, pageable, url);

        model.addAttribute("page", result.get(0));
        model.mergeAttributes((Map<String, String>)result.get(1));
        model.addAttribute("am", result.get(2).toString().contains("?")?1:0);
        model.addAttribute("url", result.get(2));

        return "cabinet";
    }
    @GetMapping("/cabinet")
    public String showCoursesCabinet (@AuthenticationPrincipal User user,
                               @PageableDefault(sort = {"startDate"},
                                       direction = Sort.Direction.ASC) Pageable pageable,
                               Model model) throws Exception{
        Page<Course> page = courseService.getAllCourses(pageable);
        model.addAttribute("page", page);
        model.addAttribute("url", "/cabinet");
        return "cabinet";
    }

    @GetMapping("/teacher/filter")
    public String courseFilterTeacher(@AuthenticationPrincipal User user,
                                      @RequestParam Map<String,String> form,
                                      @PageableDefault(sort = {"startDate"},
                                              direction = Sort.Direction.ASC) Pageable pageable,
                                      Model model){
        StringBuilder url = new StringBuilder("/teacher/filter");
        List<Object> result = courseService.statusDispatcher(form, pageable, url);

        model.addAttribute("page", result.get(0));
        model.mergeAttributes((Map<String, String>)result.get(1));
        model.addAttribute("am", result.get(2).toString().contains("?")?1:0);
        model.addAttribute("url", result.get(2));

        return "teacher";
    }

    @GetMapping("/teacher")
    public String showCoursesTeacher (@AuthenticationPrincipal User user,
                                      @PageableDefault(sort = {"startDate"},
                                              direction = Sort.Direction.ASC) Pageable pageable,
                                      Model model) throws Exception{
        Page<Course> page = courseService.getAllCourses(pageable);
        model.addAttribute("page", page);
        model.addAttribute("url", "/teacher");
        return "teacher";
    }



}
