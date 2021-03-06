package com.train.courses.dto;

import com.train.courses.entity.Course;

import java.util.List;


public class CoursesDTO {
    private List<Course> courses;

    public CoursesDTO(List<Course> courses) {
        this.courses = courses;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public CoursesDTO() {
    }
}
