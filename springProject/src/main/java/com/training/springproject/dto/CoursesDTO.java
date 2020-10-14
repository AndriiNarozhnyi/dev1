package com.training.springproject.dto;

import com.training.springproject.entity.Course;
import org.springframework.data.domain.Page;


import java.util.List;


public class CoursesDTO {
    private Page<Course> page;

    public CoursesDTO(List<Course> courses) {
        this.page = page;
    }

    public Page<Course> getCourses() {
        return page;
    }

    public void setCourses(Page<Course> courses) {
        this.page = page;
    }

    public CoursesDTO() {
    }
}
