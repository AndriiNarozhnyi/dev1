package com.training.springproject.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

import static java.time.temporal.ChronoUnit.DAYS;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String name_ukr;
    private String topic;
    private String topic_ukr;
    private LocalDate startDate;
    private LocalDate endDate;
    private long duration;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User teacher;

    @OneToMany(mappedBy = "course")
    Set<CourseTaking> courseTakings;

    public Course() {
    }

    public Course(String name, String name_ukr, String topic, String topic_ukr,
                  LocalDate startDate, LocalDate endDate, User teacher) {
        this.name = name;
        this.name_ukr = name_ukr;
        this.topic = topic;
        this.topic_ukr = topic_ukr;
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

    public String getName_ukr() {
        return name_ukr;
    }

    public void setName_ukr(String name_ukr) {
        this.name_ukr = name_ukr;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTopic_ukr() {
        return topic_ukr;
    }

    public void setTopic_ukr(String topic_ukr) {
        this.topic_ukr = topic_ukr;
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

    public Set<CourseTaking> getCourseTakings() {
        return courseTakings;
    }

    public void setCourseTakings(Set<CourseTaking> courseTakings) {
        this.courseTakings = courseTakings;
    }
}
