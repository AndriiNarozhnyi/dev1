package com.training.springproject.entity;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static java.time.temporal.ChronoUnit.DAYS;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
                ", enrolledStudents=" + enrolledStudents +
                '}';
    }
}
