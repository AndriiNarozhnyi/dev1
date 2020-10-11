package com.training.springproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Controller
public class ControllerUtils {

    static Map<String, String> getErrors(BindingResult bindingResult) {
        Collector<FieldError, ?, Map<String, String>> collector = Collectors.toMap(
                fieldError -> fieldError.getField() + "Error",
                FieldError::getDefaultMessage
        );
        return bindingResult.getFieldErrors().stream().collect(collector);
    }

    static boolean emailValid(String email) {
       final Pattern mailRegex =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

            Matcher matcher = mailRegex.matcher(email);
            return email!=null&&matcher.find();
        }


    public static boolean checkNameEmpty(String name) {
        if (name==null||name.length()==0){
            return true;
        }
        return false;
    }

    public static List<Object> checkCourseIncorrect(Map<String, String> form) {
        //TODO - transfer to controller class o learn how to get messageSourse from here
        List<Object> res = new ArrayList<>();
        Map<String, String> answer = new HashMap<>();
        boolean check = true;
        if (checkNameEmpty(form.get("name"))){
            answer.put("incname", "Course name cannot be empty");
            check = false;
        }
        if (checkNameEmpty(form.get("nameukr"))){
            answer.put("incnameukr", "Course name in Ukrainian cannot be empty");
            check = false;
        }
        if (checkNameEmpty(form.get("topic"))){
            answer.put("inctopic", "Course topic cannot be empty");
            check = false;
        }
        if (checkNameEmpty(form.get("topicukr"))){
            answer.put("inctopicukr", "Course topic in Ukrainian cannot be empty");
            check = false;
        }
        if (checkDate(LocalDate.parse(form.get("startDate")))){
            answer.put("incStartDate", "Start date cannot be before today");
            check = false;
        }
        if (checkDate(LocalDate.parse(form.get("endDate")))){
            answer.put("incEndDate", "End date cannot be before today");
            check = false;
        }
        if (LocalDate.parse(form.get("startDate")).isAfter(LocalDate.parse(form.get("endDate")))){
            answer.put("endBeforeStart", "End Date cannot be less then start Date");
            check=false;
        }
        res.add(answer);
        res.add(check);
        return res;
    }

    private static boolean checkDate(LocalDate startDate) {
        if (startDate.isBefore(LocalDate.now())){
            return true;
        }
        return false;
    }

    public static List checkUserIncorrect(Map<String, String> form, Locale locale) {
        //TODO - transfer to controller class as I cannot get messageSourse from here
        //TODO - add password check
        List<Object> res = new ArrayList<>();
        Map<String, String> answer = new HashMap<>();
        boolean check = true;
        if (checkNameEmpty(form.get("username"))){
            answer.put("incusername", "User name cannot be empty");
            check = false;
        }
        if (checkNameEmpty(form.get("usernameukr"))){
            answer.put("incusernameukr", "User name in Ukrainian cannot be empty");
            check = false;
        }
        if (!emailValid(form.get("email"))){
            answer.put("emailIncorrect", "emailIncorrect");
            check=false;
        }
        res.add(answer);
        res.add(check);
        return res;

    }
}