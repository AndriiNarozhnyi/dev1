package com.training.springproject.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static java.time.temporal.ChronoUnit.DAYS;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "Course name cannot be empty")
    private String name;
    private String nameukr;
    private String topic;
    private String topicukr;
    private LocalDate startDate;
    private LocalDate endDate;
    private long duration;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User teacher;

    @ManyToMany(fetch = FetchType.EAGER)
            @JoinTable(name = "course_taken", joinColumns = @JoinColumn(name="course_id")
            ,inverseJoinColumns = @JoinColumn(name = "user_id"))
    Set<User> enrolledStudents = new HashSet<>();

    public Set<User> getEnrolledStudents() {
        return enrolledStudents;
    }

    public void setEnrolledStudents(Set<User> enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }

    public Course() {
    }

    public Course(String name, String nameukr, String topic, String topicukr,
                  LocalDate startDate, LocalDate endDate, User teacher) {
        this.name = name;
        this.nameukr = nameukr;
        this.topic = topic;
        this.topicukr = topicukr;
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = DAYS.between(startDate, endDate);
        this.teacher = teacher;

    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public Integer getId() {
        return id;
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

    public void setNameukr(String name_ukr) {
        this.nameukr = name_ukr;
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

    public void setTopicukr(String topic_ukr) {
        this.topicukr = topic_ukr;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id.equals(course.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
