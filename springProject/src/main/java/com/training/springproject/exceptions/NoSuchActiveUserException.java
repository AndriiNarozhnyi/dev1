package com.training.springproject.exceptions;

public class NoSuchActiveUserException extends RuntimeException{
    public NoSuchActiveUserException(String errormessage){
        super(errormessage);
    }

}
