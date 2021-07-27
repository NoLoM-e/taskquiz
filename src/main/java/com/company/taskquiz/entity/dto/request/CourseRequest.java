package com.company.taskquiz.entity.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;


@Getter
@Setter
public class CourseRequest {

    @NotEmpty(message = "Course has to contain at least one task")
    @NotNull(message = "Course has to contain at least one task")
    private List<TaskRequest> tasks;

    @NotNull(message = "Course has to have owner")
    private Long ownerId;

    @NotNull(message = "Course name can't be null")
    @NotBlank(message = "Course name can't be blank")
    private String name;
}
