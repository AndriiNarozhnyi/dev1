package com.training.springproject.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.training.springproject.exceptions.NoSuchActiveUserException;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.thymeleaf.exceptions.TemplateInputException;

@ControllerAdvice
public class ExceptionHelper extends ResponseEntityExceptionHandler {
    org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger("exceptionLogger");

    @ExceptionHandler(value = {Exception.class })
    public String handleException(Exception ex, Model model) {
        logger.info("Exception: ",ex.getMessage());
        System.out.println("exception helper working");
        model.addAttribute("message", ex.getMessage());
        return "error";
    }

    @ExceptionHandler(value = { NoSuchActiveUserException.class })
    public String handleNoUserException(Exception ex, Model model) {
        logger.error("Exception: ",ex.getMessage());
        System.out.println("exception helper working");
        model.addAttribute("message1", ex.getMessage());
        return "error";
    }


}
