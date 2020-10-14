package com.training.springproject.controller;

import com.training.springproject.exceptions.NoSuchActiveUserException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHelper extends ResponseEntityExceptionHandler {
    org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger("exceptionLogger");

    @ExceptionHandler(value = {Exception.class })
    public String handleException(Exception ex, Model model) {
        logger.info("Exception: ",ex.getMessage());
        model.addAttribute("message", ex.getMessage());
        return "error";
    }

    @ExceptionHandler(value = { NoSuchActiveUserException.class })
    public String handleNoUserException(Exception ex, Model model) {
        logger.error("Exception: ",ex.getMessage());
        model.addAttribute("message", ex.getMessage());
        return "error";
    }


}
