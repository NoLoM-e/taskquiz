package com.company.taskquiz.entity.dto.request;

import com.company.taskquiz.entity.dto.response.TaskResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class UpdateCourseRequest {

    @NotNull(message = "id can't be null")
    @NotBlank(message = "id can't be blank")
    private Long id;

    @NotEmpty(message = "Course has to contain at least one task")
    @NotNull(message = "Course has to contain at least one task")
    private List<TaskResponse> tasks;

    @NotNull(message = "Course has to have owner")
    @NotBlank(message = "Course has to have owner")
    private Long ownerId;

    @NotNull(message = "Course name can't be null")
    @NotBlank(message = "Course name can't be blank")
    private String name;
}
