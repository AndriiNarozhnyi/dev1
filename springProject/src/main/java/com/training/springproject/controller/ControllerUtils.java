package com.training.springproject.controller;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
            return matcher.find();
        }


    public static boolean checkNameEmpty(String name) {
        if (name==null||name.isEmpty()){
            return true;
        }
        return false;
    }

    public static List<Object> checkCourseIncorrect(Map<String, String> form) {
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
            answer.put("endDateBeforeStart", "End Date cannot be less then start Date");
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

}