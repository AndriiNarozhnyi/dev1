package com.training.springproject.controller;

import com.training.springproject.dto.CourseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;


public class ControllerUtils {
    @Autowired
    private MessageSource messageSource;

    public ControllerUtils(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public ControllerUtils() {

    }

    Map<String, String> getErrors(BindingResult bindingResult, Locale locale) {
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

    private static boolean passwordCheckIncorrect(String password) {
        final Pattern passwordRegex =
                Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,32}$");
        Matcher matcher = passwordRegex.matcher(password);
        return !matcher.find();
    }


    public static boolean checkNameEmpty(String name) {
        if (name==null||name.length()==0){
            return true;
        }
        return false;
    }

    public List<Object> checkCourseIncorrect(Map<String, String> form, Locale locale) {
        List<Object> res = new ArrayList<>();
        Map<String, String> answer = new HashMap<>();
        boolean check = true;
        if (checkNameEmpty(form.get("name"))){
            answer.put("incname", messageSource.getMessage("incname", null, locale));
            check = false;
        }
        if (checkNameEmpty(form.get("nameukr"))){
            answer.put("incnameukr", messageSource.getMessage("incnameukr",null, locale));
            check = false;
        }
        if (checkNameEmpty(form.get("topic"))){
            answer.put("inctopic", messageSource.getMessage("inctopic", null, locale));
            check = false;
        }
        if (checkNameEmpty(form.get("topicukr"))){
            answer.put("inctopicukr", messageSource.getMessage("inctopicukr", null, locale));
            check = false;
        }
        if (checkDate(form.get("startDate"))){
            answer.put("incStartDate", "Start date cannot be empty or before today");
            check = false;
        }
        if (checkDate(form.get("endDate"))){
            answer.put("incEndDate", messageSource.getMessage("incEndDate", null, locale));
            check = false;
        }
        if ((!(answer.containsKey("incStartDate")||answer.containsKey("incendtDate")))&&
                (LocalDate.parse(form.get("startDate")).isAfter(LocalDate.parse(form.get("endDate"))))){
            answer.put("endBeforeStart", messageSource.getMessage("endBeforeStart", null, locale));
            check=false;
        }
        res.add(answer);
        res.add(check);
        return res;
    }

    private static boolean checkDate(String date) {
        if (date==null||date.length()==0||
                LocalDate.parse(date).isBefore(LocalDate.now())){
            return true;
        }
        return false;
    }

    public List checkUserIncorrect(Map<String, String> form, Locale locale) {
        List<Object> res = new ArrayList<>();
        Map<String, String> answer = new HashMap<>();
        boolean check = true;
        if (checkNameEmpty(form.get("username"))){
            answer.put("incusername", messageSource.getMessage("incusername", null,locale));
            check = false;
        }
        if (checkNameEmpty(form.get("usernameukr"))){
            answer.put("incusernameukr", messageSource.getMessage("incusernameukr",null,locale));
            check = false;
        }
        if (!emailValid(form.get("email"))){
            answer.put("emailIncorrect", messageSource.getMessage("emailIncorrect", null, locale));
            check=false;
        }
        if (passwordCheckIncorrect(form.get("password"))){
            answer.put("incpassword", messageSource.getMessage("incpassword", null, locale));
            check=false;
        }
        res.add(answer);
        res.add(check);
        return res;

    }
    public List<Object> checkCourseEditIncorrect(Map<String, String> form, CourseDTO courseDTO, Locale locale) {
        List<Object> res = new ArrayList<>();
        Map<String, String> answer = new HashMap<>();
        boolean check = true;
        if (checkNameEmpty(form.get("name"))){
            answer.put("incname", messageSource.getMessage("incname", null, locale));
            check = false;
        }
        if (checkNameEmpty(form.get("nameukr"))){
            answer.put("incnameukr", messageSource.getMessage("incnameukr",null, locale));
            check = false;
        }
        if (checkNameEmpty(form.get("topic"))){
            answer.put("inctopic", messageSource.getMessage("inctopic", null, locale));
            check = false;
        }
        if (checkNameEmpty(form.get("topicukr"))){
            answer.put("inctopicukr", messageSource.getMessage("inctopicukr", null, locale));
            check = false;
        }
        if(courseDTO.isNotStarted()){
            if (checkDate(form.get("startDate"))){
            answer.put("incStartDate", messageSource.getMessage("incStartDate", null, locale));
            check = false;
        }
        }
        if (checkDate(form.get("endDate"))){
            answer.put("incEndDate", messageSource.getMessage("incEndDate", null, locale));
            check = false;
        }
        if ((!(answer.containsKey("incStartDate")||answer.containsKey("incEndDate")))&&
                (LocalDate.parse(form.get("startDate")).isAfter(LocalDate.parse(form.get("endDate"))))){
            answer.put("endBeforeStart", messageSource.getMessage("endBeforeStart", null, locale));
            check=false;
        }
        res.add(answer);
        res.add(check);
        return res;
    }

}