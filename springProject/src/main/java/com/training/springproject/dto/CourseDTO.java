package com.training.springproject.dto;

import java.time.LocalDate;

public class CourseDTO {
    private Integer id;
    private String name;
    private String name_ukr;
    private String topic;
    private String topic_ukr;
    private LocalDate startDate;
    private LocalDate endDate;
    private long duration;

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

    public CourseDTO(Integer id, String name, String name_ukr, String topic, String topic_ukr, LocalDate startDate, LocalDate endDate, long duration) {
        this.id = id;
        this.name = name;
        this.name_ukr = name_ukr;
        this.topic = topic;
        this.topic_ukr = topic_ukr;
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = duration;
    }
}
