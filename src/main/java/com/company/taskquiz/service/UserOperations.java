package com.company.taskquiz.service;

import com.company.taskquiz.entity.dto.request.AddToUserScoreRequest;
import com.company.taskquiz.entity.dto.request.UpdateUserRequest;
import com.company.taskquiz.entity.dto.request.UserRequest;
import com.company.taskquiz.entity.dto.response.UserResponse;

import java.util.List;
import java.util.Optional;

public interface UserOperations {

    Optional<UserResponse> findById(Long id);
    List<UserResponse> findAll();
    UserResponse create(UserRequest request);
    UserResponse createAdmin(UserRequest request);
    UserResponse update(UpdateUserRequest request);
    void delete(Long id);
    List<UserResponse> getLeaders(Long num);
    UserResponse addToScore(AddToUserScoreRequest request);
}
