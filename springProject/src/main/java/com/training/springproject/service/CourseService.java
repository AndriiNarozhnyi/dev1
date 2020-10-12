package com.training.springproject.service;

import com.training.springproject.dto.CoursesDTO;
import com.training.springproject.entity.Course;
import com.training.springproject.entity.User;
import com.training.springproject.exceptions.CourseNotFoundException;
import com.training.springproject.exceptions.NoSuchActiveUserException;
import com.training.springproject.repository.CourseRepository;
import com.training.springproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class CourseService {
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger("eventLogger");

    @Autowired
    public CourseService(CourseRepository courseRepository, UserRepository userRepository){
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    public CoursesDTO getAllCourses() {
        return new CoursesDTO(courseRepository.findAll());
    }

    public void saveNewCourse(Course course){
        logger.info("new course created " + course.toString());
        try {
        courseRepository.save(course);
        } catch (Exception ex){
        }
    }

    public Optional<Course> findById(Integer courseId) {
        return courseRepository.findById(courseId);
    }

    public boolean updateCourse(Course course) {
        courseRepository.save(course);
        logger.info("course updated " + course.toString());
        return true;
    }

    public boolean enrollUser(Integer courseId, User user) {
        Course course = findById(courseId).get();
        course.getEnrolledStudents().add(user);
        updateCourse(course);
        //TODO - change to native Query
        return true;
    }

    public boolean unenrollUser(Integer courseId, User user) {
        Course course = findById(courseId).get();
        course.getEnrolledStudents().remove(user);
        updateCourse(course);
        //TODO - change to native Query
        return true;
    }

    public List<Object> findByFiter(Map<String, String> form) {
        List<Object> result = new ArrayList<>();
        Map<String,String> map = new HashMap<>();
        CoursesDTO courses = new CoursesDTO(
                courseRepository.findByNameLikeAndNameukrLikeAndTopicLikeAndTopicukrLikeAndStartDateAfterAndDurationGreaterThanEqualAndDurationLessThanEqualAndEndDateBeforeAndTeacherUsernameLikeOrderByStartDateAsc(
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

    public boolean editCourse(Integer courseId, Map<String, String> form) throws Exception {
        Course course = findById(courseId).orElseThrow(()->new CourseNotFoundException("course was deleted, apply to admin for logs"));
        course.setName(form.get("name"));
        course.setNameukr(form.get("nameukr"));
        course.setTopic(form.get("topic"));
        course.setTopicukr(form.get("topicukr"));
        course.setStartDate(LocalDate.parse(form.get("startDate")).plusDays(1));
        course.setEndDate(LocalDate.parse(form.get("endDate")).plusDays(1));
        course.setDuration(DAYS.between(LocalDate.parse(form.get("startDate")), LocalDate.parse(form.get("endDate"))));

        return saveEditedCourse(course, form);

    }
@Transactional // I think here it may not be necessary
boolean saveEditedCourse(Course course, Map<String, String> form) throws Exception {
        if (!form.get("newTeacherId").equals("0")){
            User newTeacher = userRepository.findByIdAndActiveTrue(Long.parseLong(form.get("newTeacherId")))
                    .orElseThrow(()->new NoSuchActiveUserException("No active teacher by this id"));

            if(newTeacher.isTeacher()){
                course.setTeacher(newTeacher);
            } else {
                throw new NoSuchActiveUserException("No active teacher by this id");
            }
        }
        courseRepository.save(course);
    return true;
    }

    public boolean checkNameDateTeacher(String name, String startDate, User user) {
        if(courseRepository.findByNameAndStartDateAndTeacherUsername(name,
                LocalDate.parse(startDate), user.getUsername()).isPresent()){
            return true;
        }
        return false;
    }
}
