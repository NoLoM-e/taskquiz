package com.company.taskquiz.entity.dto.response;

import com.company.taskquiz.entity.Course;
import com.company.taskquiz.entity.QuizUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@AllArgsConstructor
public class CourseResponse {

    private  Long id;

    private String name;

    private Long ownerId;

    private List<TaskResponse> questions;

    private List<Long> accessed;

    public static CourseResponse fromCourse(Course course){
        return new CourseResponse(
                course.getId(),
                course.getName(),
                course.getOwner().getId(),
                course.getTasks().stream().map(TaskResponse::fromTask).collect(Collectors.toList()),
                course.getAccessed().stream().map(QuizUser::getId).collect(Collectors.toList()));
    }
}
