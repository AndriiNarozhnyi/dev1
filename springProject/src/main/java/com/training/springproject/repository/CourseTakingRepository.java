package com.training.springproject.repository;

import com.training.springproject.entity.Course;
import com.training.springproject.entity.CourseTaking;
import com.training.springproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface CourseTakingRepository extends JpaRepository<CourseTaking, Long> {

    @Query(value = "select ct.course_id from course_taking ct inner join user u on ct.user_id=u.id " +
            "where u.id = :userId",
                    nativeQuery = true)
    Set<Integer>findCourseIdByUserId(@Param("userId") long userId);
}
