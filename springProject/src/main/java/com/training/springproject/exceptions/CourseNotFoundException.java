package com.training.springproject.exceptions;

public class CourseNotFoundException extends RuntimeException{
    public CourseNotFoundException(String errormessage){
        super(errormessage);
    }
}
