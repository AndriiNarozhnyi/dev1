package com.training.springproject.exceptions;

public class NoSuchCourseException extends RuntimeException {
    public NoSuchCourseException(String erromessage) {
        super(erromessage);
    }
}
