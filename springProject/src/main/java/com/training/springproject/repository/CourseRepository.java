package com.training.springproject.repository;

import com.training.springproject.entity.Course;
import com.training.springproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findById(Integer courseId);
    List<Course> findByNameLikeAndNameukrLikeAndTopicLikeAndTopicukrLike(String name, String nameukr, String topic, String topicukr);
    Optional<Course> findByNameAndStartDateAndTeacherUsername(String name, LocalDate startDate, String username_ukr);
    List<Course> findByNameLikeAndNameukrLikeAndTopicLikeAndTopicukrLikeAndStartDateAfterAndDurationGreaterThanEqualAndDurationLessThanEqualAndEndDateBeforeAndTeacherUsernameLikeOrderByStartDateAsc(String fname, String fnameukr, String ftopic, String ftopicukr, LocalDate parse, long parseLong, long parseLong1, LocalDate parse1, String fteacher);







    List<Course> findByTopic(String topic);
    List<Course> findByNameLike(String name);

    Course findByName(String courseName);


    List<Course> findByNameLikeAndNameukrLike(String name, String nameukr);

    List<Course> findByStartDateAfter(LocalDate fstartDate);

    List<Course> findByNameLikeAndNameukrLikeAndTopicLikeAndTopicukrLikeAndStartDateAfterAndDurationGreaterThanEqualAndDurationLessThanEqualAndEndDateBefore(String fname, String fnameukr, String ftopic, String ftopicukr, LocalDate fstartDate, Long fdurationMin, Long fdurationMax, LocalDate fendDate);

    List<Course> findByNameLikeAndNameukrLikeAndTopicLikeAndTopicukrLikeAndStartDateAfterAndDurationGreaterThanEqualAndDurationLessThanEqual(String fname, String fnameukr, String ftopic, String ftopicukr, LocalDate fstartDate, long fdurationMin, long fdurationMax);

    List<Course> findByNameLikeAndNameukrLikeAndTopicLikeAndTopicukrLikeAndStartDateAfterAndDurationGreaterThanEqualAndDurationLessThanEqualAndEndDateBeforeAndTeacherUsernameLike(String fname, String fnameukr, String ftopic, String ftopicukr, LocalDate parse, long parseLong, long parseLong1, LocalDate parse1, String fteacher);


}
