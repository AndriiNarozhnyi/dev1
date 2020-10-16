package com.training.springproject.dto;

import com.training.springproject.entity.Course;
import com.training.springproject.entity.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static java.time.temporal.ChronoUnit.DAYS;

public class CourseDTO {
    private Integer id;
    private String name;
    private String nameukr;
    private String topic;
    private String topicukr;
    private LocalDate startDate;
    private LocalDate endDate;
    private long duration;
    private UserDTO teacher;
    Set<UserDTO> enrolledStudents = new HashSet<>();

    public CourseDTO(Integer id, String name, String nameukr, String topic, String topicukr, LocalDate startDate, LocalDate endDate, long duration, UserDTO teacher) {
        this.id = id;
        this.name = name;
        this.nameukr = nameukr;
        this.topic = topic;
        this.topicukr = topicukr;
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = duration;
        this.teacher = teacher;
    }

    public CourseDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameukr() {
        return nameukr;
    }

    public void setNameukr(String nameukr) {
        this.nameukr = nameukr;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTopicukr() {
        return topicukr;
    }

    public void setTopicukr(String topicukr) {
        this.topicukr = topicukr;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public UserDTO getTeacher() {
        return teacher;
    }

    public void setTeacher(UserDTO teacher) {
        this.teacher = teacher;
    }

    public Set<UserDTO> getEnrolledStudents() {
        return enrolledStudents;
    }
    public Set<Long> getEnrolledStudentsId(){
        Set<Long> Ids = new HashSet<>();
        Set<UserDTO>usr = getEnrolledStudents();
        for (UserDTO userDTO:usr){
            Ids.add(userDTO.getId());
        }
        return Ids;
    }

    public void setEnrolledStudents(Set<UserDTO> enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseDTO courseDTO = (CourseDTO) o;
        return id.equals(courseDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public boolean isStarted(){
        return LocalDate.now().isAfter(startDate)&&LocalDate.now().isBefore(endDate);
    }

    public boolean isFinished(){
        return LocalDate.now().isAfter(endDate);
    }

    public boolean isNotStarted(){
        return LocalDate.now().isBefore(startDate);
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nameukr='" + nameukr + '\'' +
                ", topic='" + topic + '\'' +
                ", topicukr='" + topicukr + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", duration=" + duration +
                ", teacher=" + teacher +
                '}';
    }
}
