package com.company.taskquiz.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public final class TaskQuizExceptions extends Exception{

    public TaskQuizExceptions(){}

    public static ResponseStatusException userNotFound(Long id){
        return new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User with id %d not found", id));
    }

    public static ResponseStatusException duplicateEmail(String email){
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("User with email %s already exists", email));
    }

    public static ResponseStatusException duplicateLogin(String login){
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("User with login %s already exists", login));
    }

    public static ResponseStatusException authorityNotFound(String value) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "User authority " + value + " not defined");
    }

    public static ResponseStatusException duplicateCourseName(String name){
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Course with name %s already exists", name));
    }

    public static ResponseStatusException courseNotFound(Long id){
        return new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Course with id %d not found", id));
    }

    public static ResponseStatusException notAnOwner(Long userId, Long courseId){
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("User with id %d is not an owner of course with id %d", userId, courseId));
    }

    public static ResponseStatusException notPermittedToCourse(Long userId, Long courseId){
        return new ResponseStatusException(HttpStatus.FORBIDDEN, String.format("User with id %d is not permitted to have access to course with id %d", userId, courseId));
    }

    public static ResponseStatusException tooManyQuestions(Long courseId, Long num){
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Cannot generate %d questions for course with id %d", num, courseId));
    }
}
