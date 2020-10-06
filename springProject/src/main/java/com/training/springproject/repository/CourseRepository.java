package com.training.springproject.repository;

import com.training.springproject.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByTopic(String topic);
    List<Course> findByNameLike(String name);

    Course findByName(String courseName);

    Optional<Course> findById(Integer courseId);
}
