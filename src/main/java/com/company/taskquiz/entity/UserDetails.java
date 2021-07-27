package com.company.taskquiz.entity;

import org.springframework.security.core.userdetails.User;

import java.util.EnumSet;
import java.util.List;


public class UserDetails extends User
{

    private final QuizUser source;


    public UserDetails(QuizUser source){
        super(source.getEmail(),
                source.getPassword(),
                source.isActive(),
                true,
                true,
                true,
                EnumSet.copyOf(source.getRoles().keySet()));

        this.source = source;
    }

    public QuizUser getSource(){
        return this.source;
    }

}
