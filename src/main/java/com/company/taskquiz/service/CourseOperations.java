package com.company.taskquiz.service;

import com.company.taskquiz.entity.dto.request.ChangeCoursePermittedRequest;
import com.company.taskquiz.entity.dto.request.CourseRequest;
import com.company.taskquiz.entity.dto.request.UpdateCourseRequest;
import com.company.taskquiz.entity.dto.response.CourseResponse;
import com.company.taskquiz.entity.dto.response.TaskResponse;

import java.util.List;
import java.util.Optional;

public interface CourseOperations {

    Optional<CourseResponse> findById(Long id, Long userId);
    Optional<CourseResponse> findByName(String name);
    List<CourseResponse> findAll();
    List<CourseResponse> findByOwnerId(Long id);
    CourseResponse create(CourseRequest request);
    CourseResponse update(UpdateCourseRequest request);
    CourseResponse updatePermitted(ChangeCoursePermittedRequest request, Long courseId, Long userId);
    void delete(Long id);
    List<TaskResponse> generateTest(Long id, Long num);
}
