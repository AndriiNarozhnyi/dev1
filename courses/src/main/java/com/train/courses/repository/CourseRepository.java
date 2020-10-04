package com.train.courses.repository;

import com.train.courses.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByTopic(String topic);
    List<Course> findByNameLike(String name);
}
