package com.training.springproject.entity;

import javax.persistence.*;
import java.util.Objects;
import java.util.Optional;

@Entity
public class CourseTaking {
    @EmbeddedId
    CourseTakingKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name="user_id")
    User student;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name="course_id")
    Course course;
    String homework;
    String responce;
    int mark;

    public CourseTaking() {
    }

    public CourseTaking(User student, Course course) {
        this.student = student;
        this.course = course;
    }

    public CourseTakingKey getId() {
        return id;
    }

    public void setId(CourseTakingKey id) {
        this.id = id;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Optional<Course> course) {

        this.course = course.get();
    }

    public String getHomework() {
        return homework;
    }

    public void setHomework(String homework) {
        this.homework = homework;
    }

    public String getResponce() {
        return responce;
    }

    public void setResponce(String responce) {
        this.responce = responce;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        return "CourseTaking{" +
                "id=" + id +
                ", student=" + student +
                ", course=" + course +
                ", homework='" + homework + '\'' +
                ", responce='" + responce + '\'' +
                ", mark=" + mark +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseTaking that = (CourseTaking) o;
        return mark == that.mark &&
                id.equals(that.id) &&
                student.equals(that.student) &&
                course.equals(that.course) &&
                Objects.equals(homework, that.homework) &&
                Objects.equals(responce, that.responce);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, student, course, homework, responce, mark);
    }
}
