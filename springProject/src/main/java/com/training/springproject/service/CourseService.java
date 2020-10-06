package com.training.springproject.service;

import com.training.springproject.dto.CoursesDTO;
import com.training.springproject.entity.Course;
import com.training.springproject.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
}
