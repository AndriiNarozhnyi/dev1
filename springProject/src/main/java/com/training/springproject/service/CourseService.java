package com.training.springproject.service;

import com.training.springproject.dto.CourseDTO;
import com.training.springproject.entity.Course;
import com.training.springproject.entity.User;
import com.training.springproject.exceptions.CourseNotFoundException;
import com.training.springproject.exceptions.NoSuchActiveUserException;
import com.training.springproject.exceptions.NoSuchCourseException;
import com.training.springproject.repository.CourseRepository;
import com.training.springproject.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.expression.ParseException;
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
    private ModelMapper modelMapper;

    @Autowired
    public CourseService(CourseRepository courseRepository, UserRepository userRepository){
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    public Page<CourseDTO> getAllCourses(Pageable pageable) {
        Page<Course> coursePage = courseRepository.findAll(pageable);
        List<CourseDTO> dtos = new ArrayList<>();
        for (Course course : coursePage){
            dtos.add(convertToDto(course));
        }
        return new PageImpl<>(dtos, pageable, coursePage.getTotalElements());
    }

    public void saveNewCourse(Course course, Long id){
        try {
        courseRepository.save(course);
        } catch (Exception ex){
        }
        logger.info("User id=" + id + " created " + course.toString());
    }

    public Optional<Course> findById(Integer courseId) {
        return courseRepository.findById(courseId);
    }
    public CourseDTO findByIdOut(Integer courseId) {
        return convertToDto(courseRepository.findById(courseId)
                .orElseThrow(()-> new NoSuchCourseException("No such course")));
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

    public Page<Course> findByFilter(Map<String, String> form, Pageable pageable) {
        return courseRepository.findByNameLikeAndNameukrLikeAndTopicLikeAndTopicukrLikeAndStartDateAfterAndDurationGreaterThanEqualAndDurationLessThanEqualAndEndDateBeforeAndTeacherUsernameLikeOrderByStartDateAsc(
                "%"+form.get("fname")+"%",
                "%"+form.get("fnameukr")+"%",
                "%"+form.get("ftopic")+"%",
                "%"+form.get("ftopicukr")+"%",
                        LocalDate.parse(form.get("fstartDate")==""?"0002-02-02":form.get("fstartDate")),
                        Long.parseLong(form.get("fdurationMin")==""?"0":form.get("fdurationMin")),
                        Long.parseLong(form.get("fdurationMax")==""?"9999":form.get("fdurationMax")),
                        LocalDate.parse(form.get("fendDate")==""?"9999-02-02":form.get("fendDate")),
                        "%"+form.get("fteacher")+"%",
                pageable
                );
    }
    public Page<Course> findByFilterIP(Map<String, String> form, Pageable pageable) {
        return courseRepository.findByNameLikeAndNameukrLikeAndTopicLikeAndTopicukrLikeAndStartDateBeforeAndDurationGreaterThanEqualAndDurationLessThanEqualAndEndDateAfterAndTeacherUsernameLikeOrderByStartDateAsc(
                "%" + form.get("fname") + "%",
                "%" + form.get("fnameukr") + "%",
                "%" + form.get("ftopic") + "%",
                "%" + form.get("ftopicukr") + "%",
                LocalDate.parse(form.get("fstartDate") == "" ? "0002-02-02" : form.get("fstartDate")),
                Long.parseLong(form.get("fdurationMin") == "" ? "0" : form.get("fdurationMin")),
                Long.parseLong(form.get("fdurationMax") == "" ? "9999" : form.get("fdurationMax")),
                LocalDate.parse(form.get("fendDate") == "" ? "9999-02-02" : form.get("fendDate")),
                "%" + form.get("fteacher") + "%",
                pageable
        );
    }

    private void queryAndUrlProcessor(Map<String, String> form, Map<String, String> map, StringBuilder url) {
        Iterator it = form.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            char first = ((String)pair.getKey()).charAt(0);
            if(first=='f'){
                map.put((String)pair.getKey(), (String)pair.getValue());
                String key = (String)pair.getKey();
                if (key.equals("fname")){
                    url.append("?");
                } else {
                    url.append("&");
                }
                url.append(key);
                url.append("=");
                url.append(pair.getValue());
            }
//            it.remove(); // avoids a ConcurrentModificationException
        }
    }

    public boolean editCourse(Integer courseId, Map<String, String> form, Long id) throws Exception {
        Course course = findById(courseId).orElseThrow(()->new CourseNotFoundException("course was deleted, apply to admin for logs"));
        course.setName(form.get("name"));
        course.setNameukr(form.get("nameukr"));
        course.setTopic(form.get("topic"));
        course.setTopicukr(form.get("topicukr"));
        course.setStartDate(LocalDate.parse(form.get("startDate")).plusDays(1));
        course.setEndDate(LocalDate.parse(form.get("endDate")).plusDays(1));
        course.setDuration(DAYS.between(LocalDate.parse(form.get("startDate")), LocalDate.parse(form.get("endDate"))));

        return saveEditedCourse(course, form, id);

    }
@Transactional // I think here it may not be necessary
boolean saveEditedCourse(Course course, Map<String, String> form, Long id) throws Exception {
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
        logger.info("User id = "+ id +" edited course " +course.toString());


    return true;
    }

    public boolean checkNameDateTeacher(String name, String startDate, User user) {
        if(courseRepository.findByNameAndStartDateAndTeacherUsername(name,
                LocalDate.parse(startDate), user.getUsername()).isPresent()){
            return true;
        }
        return false;
    }

    public List<Object> statusDispatcher(Map<String, String> form, Pageable pageable, StringBuilder url) {
        List<Object> result = new ArrayList<>();
        Map<String,String> map = new HashMap<>();
        Page<Course> page;
        queryAndUrlProcessor(form, map, url);
        switch (form.get("fstatus")) {
            case "Finished":
                form.put("fendDate", LocalDate.now().plusDays(1).toString());
                page = findByFilter(form, pageable);
                break;
            case "Not Started":
                form.put("fstartDate", LocalDate.now().plusDays(1).toString());
                page = findByFilter(form, pageable);
                break;
            case "In Progress":
                form.put("fstartDate", LocalDate.now().plusDays(2).toString());
                form.put("fendDate", LocalDate.now().plusDays(0).toString());
                page = findByFilterIP(form, pageable);
                break;
            default:
                page = findByFilter(form, pageable);
                break;
        }
        Page<CourseDTO> pageDTO = toDtoPage(page, pageable);

                result.add(pageDTO);
                result.add(map);
                result.add(url.toString());
                return result;

    }
    @Transactional
    public boolean delete(Integer id) {
        Course course = courseRepository.findById(id).orElseThrow(()->new NoSuchCourseException("Course does not exist"));
        course.getEnrolledStudents().clear();
        courseRepository.save(course);
            courseRepository.delete(course);
            return true;
    }
    private CourseDTO convertToDto(Course course) {
        CourseDTO courseDTO = modelMapper.map(course, CourseDTO.class);
        return courseDTO;
    }

    private Course convertToCourse(CourseDTO courseDTO) throws ParseException {
        Course course = modelMapper.map(courseDTO, Course.class);
        return course;
    }
    private Page<CourseDTO> toDtoPage(Page<Course> coursePage, Pageable pageable){
        List<CourseDTO> dtos = new ArrayList<>();
        for (Course course : coursePage){
            dtos.add(convertToDto(course));
        }
        return new PageImpl<>(dtos, pageable, coursePage.getTotalElements());
    }

}
