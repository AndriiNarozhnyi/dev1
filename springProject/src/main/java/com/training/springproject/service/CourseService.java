package com.training.springproject.service;

import com.training.springproject.dto.CoursesDTO;
import com.training.springproject.entity.Course;
import com.training.springproject.entity.User;
import com.training.springproject.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository){
        this.courseRepository = courseRepository;
    }

    public CoursesDTO getAllCourses() {
        //TODO checking for an empty course list
        return new CoursesDTO(courseRepository.findAll());
    }
    public CoursesDTO findByNameLike(String fname){
        return new CoursesDTO(courseRepository.findByNameLike("%"+fname+"%"));
    }
    public void saveNewCourse(Course course){
        try {
        courseRepository.save(course);
        } catch (Exception ex){
        }
    }

    public Course findByName(String courseName) {
       return courseRepository.findByName(courseName);
    }

    public Optional<Course> findById(Integer courseId) {
        System.out.println(courseId.getClass());
        return courseRepository.findById(courseId);
    }

    public void updateCourse(Course course) {
        courseRepository.save(course);
    }

    public void enrollUser(Integer courseId, User user) {
        Course course = findById(courseId).get();
        course.getEnrolledStudents().add(user);
        updateCourse(course);
        //TODO - change to native Query
    }

    public void unenrollUser(Integer courseId, User user) {
        Course course = findById(courseId).get();
        course.getEnrolledStudents().remove(user);
        updateCourse(course);
        //TODO - change to native Query
    }

    public List<Object> findByFiter(Map<String, String> form) {
        List<Object> result = new ArrayList<>();
        Map<String,String> map = new HashMap<>();
        CoursesDTO courses = new CoursesDTO(
                courseRepository.findByNameLikeAndNameukrLikeAndTopicLikeAndTopicukrLikeAndStartDateAfterAndDurationGreaterThanEqualAndDurationLessThanEqualAndEndDateBeforeAndTeacherUsernameLike(
                "%"+form.get("fname")+"%",
                "%"+form.get("fnameukr")+"%",
                "%"+form.get("ftopic")+"%",
                "%"+form.get("ftopicukr")+"%",
                        LocalDate.parse(form.get("fstartDate")==""?"0002-02-02":form.get("fstartDate")),
                        Long.parseLong(form.get("fdurationMin")==""?"0":form.get("fdurationMin")),
                        Long.parseLong(form.get("fdurationMax")==""?"9999":form.get("fdurationMax")),
                        LocalDate.parse(form.get("fendDate")==""?"9999-02-02":form.get("fendDate")),
                        "%"+form.get("fteacher")+"%"
                ));

        result.add(courses);

        map.put("fname", form.get("fname"));
        map.put("fnameukr", form.get("fnameukr"));
        map.put("ftopic", form.get("ftopic"));
        map.put("ftopicukr", form.get("ftopicukr"));
        map.put("fstartDate", form.get("fstartDate"));
        map.put("fendDate", form.get("fendDate"));
        map.put("fdurationMin", form.get("fdurationMin"));
        map.put("fdurationMax", form.get("fdurationMax"));
        map.put("fteacher", form.get("fteacher"));

        result.add(map);
        return result;
    }
    public List<Object> findByFiterTest(Map<String, String> form) {
        List<Object> result = new ArrayList<>();
        Map<String,String> map = new HashMap<>();
        CoursesDTO courses = new CoursesDTO(
                courseRepository.findByStartDateAfter(

                        LocalDate.parse((form.get("fstartDate")==""?"0001-01-01":form.get("fstartDate"))

                )));

        result.add(courses);
        map.put("fstartDate", form.get("fstartDate"));
        result.add(map);
        return result;
    }
}
